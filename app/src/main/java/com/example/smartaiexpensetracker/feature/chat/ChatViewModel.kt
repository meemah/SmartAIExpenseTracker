package com.example.smartaiexpensetracker.feature.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartaiexpensetracker.core.repos.ChatRepo
import com.skydoves.sandwich.onException
import androidx.compose.runtime.State
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    val chatRepo: ChatRepo
) : ViewModel() {
    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: List<ChatMessage> = _messages

    private val _isTyping = mutableStateOf(false)
    val isTyping: State<Boolean> = _isTyping


    fun sendMessage(message: String) {
        val trimmedMessage = message.trim()
        if (trimmedMessage.isEmpty()) return
        _messages.add(ChatMessage(role = ChatRole.USER, text = trimmedMessage))
        viewModelScope.launch {
            _isTyping.value = true
            chatRepo.chat(message = trimmedMessage).onSuccess {
                _messages.add(ChatMessage(role = ChatRole.ASSISTANT, text = data.data.reply))
            }.onException {
                ChatMessage(
                    text = throwable.message ?: "Something went wrong",
                    role = ChatRole.ASSISTANT
                )
            }
        }
        _isTyping.value = false
    }
}
