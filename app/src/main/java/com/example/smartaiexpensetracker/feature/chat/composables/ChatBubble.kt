package com.example.smartaiexpensetracker.feature.chat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.smartaiexpensetracker.feature.chat.ChatMessage
import com.example.smartaiexpensetracker.feature.chat.ChatRole

@Composable
fun ChatBubble( msg: ChatMessage) {
    val isUser = msg.role == ChatRole.USER
    val userColor: Color = Color.Green
    val systemColor: Color = Color.Gray
    Row(
      horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (isUser) userColor else systemColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                )
        ) {
            Text(
                msg.text, style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White
                )
            )
        }
    }

}


@Preview
@Composable
fun ChatBubblePreview() {
    ChatBubble(
        msg = ChatMessage(
            text = "Hello",
            role = ChatRole.USER
        )
    )
}