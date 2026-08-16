package com.example.smartaiexpensetracker.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.theme.customColors
import com.example.smartaiexpensetracker.feature.chat.composables.ChatBubble
import com.example.smartaiexpensetracker.feature.chat.composables.ChatInput
import com.example.smartaiexpensetracker.feature.chat.composables.TypingIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatView(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val messages = chatViewModel.messages
    val listState = rememberLazyListState()
    val isTyping by chatViewModel.isTyping
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.customColors.background),
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(20.dp)
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages, key = { it.id }) { msg ->
                        ChatBubble(msg)
                    }
                    if (isTyping) {
                        item {
                            TypingIndicator()
                        }
                    }
                }
                ChatInput(onSend = chatViewModel::sendMessage)
            }
        }

    }

}


