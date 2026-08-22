package com.example.smartaiexpensetracker.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.theme.customColors
import com.example.smartaiexpensetracker.feature.chat.composables.ChatBubble
import com.example.smartaiexpensetracker.feature.chat.composables.ChatInput
import com.example.smartaiexpensetracker.feature.chat.composables.TypingIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    modifier: Modifier = Modifier, chatViewModel: ChatViewModel = hiltViewModel()
) {
    val messages = chatViewModel.messages
    val listState = rememberLazyListState()
    val isTyping by chatViewModel.isTyping
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }
    val suggestedQuestions = listOf<String>(
        "How much have I spent this month?", "Top Spending categories?", "Remaining budget?"
    )
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.customColors.background),
        topBar = {
            val colors = MaterialTheme.customColors
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(colors.background),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = colors.onPrimaryContainer,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.SmartToy,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                "AI Assistant",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onSurface
                                )
                            )
                            Text(
                                "Log expense, set budget, ask questions",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = colors.slate
                                )
                            )
                        }
                    }
                }
            )
        }) { innerPadding ->
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
                        ChatBubble(
                            msg = msg,
                            onRetry = if (msg.isError) {
                                { chatViewModel.retry(msg) }
                            } else null
                        )
                    }
                    if (isTyping) {
                        item {
                            TypingIndicator()
                        }
                    }
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    items(suggestedQuestions) { question ->

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable(onClick = {
                                    chatViewModel.sendMessage(question)
                                })
                                .glassCard()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                Icons.Outlined.QrCode,
                                contentDescription = null,
                                tint = MaterialTheme.customColors.surface
                            )
                            Text(
                                question,
                                style = TextStyle(
                                    color = MaterialTheme.customColors.onSurfaceVariant,
                                ),
                            )
                        }


                    }
                }
                ChatInput(onSend = chatViewModel::sendMessage, isTyping = isTyping)
            }
        }

    }

}


