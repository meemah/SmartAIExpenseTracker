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


    fun retry(errorMessage: ChatMessage) {
        val errorIndex = _messages.indexOf(errorMessage)
        if (errorIndex < 0) return
        val userMessage = _messages.getOrNull(errorIndex - 1)?.takeIf { it.role == ChatRole.USER }
            ?: return
        _messages.removeAt(errorIndex)
        sendMessage(userMessage.text, addUserMessage = false)
    }

    fun sendMessage(message: String, addUserMessage: Boolean = true) {
        val trimmedMessage = message.trim()
        if (trimmedMessage.isEmpty()) return
        if (addUserMessage) _messages.add(ChatMessage(role = ChatRole.USER, text = trimmedMessage))
        viewModelScope.launch {
            _isTyping.value = true
            chatRepo.chat(message = trimmedMessage).onSuccess {
                _messages.add(ChatMessage(role = ChatRole.ASSISTANT, text = data.data.reply))
            }.onException {
                _messages.add(ChatMessage(
                    text = throwable.message ?: "Something went wrong",
                    role = ChatRole.ASSISTANT,
                    isError = true
                ))
            }
            _isTyping.value = false
        }
    }
}
