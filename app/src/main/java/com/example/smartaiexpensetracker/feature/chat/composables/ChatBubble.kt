package com.example.smartaiexpensetracker.feature.chat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.theme.customColors

import com.example.smartaiexpensetracker.feature.chat.ChatMessage
import com.example.smartaiexpensetracker.feature.chat.ChatRole

@Composable
fun ChatBubble(msg: ChatMessage, onRetry: (() -> Unit)? = null) {
    val isUser = msg.role == ChatRole.USER
    val colors = MaterialTheme.customColors
    val errorColor = Color(0xFFD32F2F)

    val bubbleColor = when {
        isUser -> colors.onPrimaryContainer
        msg.isError -> errorColor.copy(alpha = 0.1f)
        else -> colors.surfaceContainer
    }

    val bubbleShape = RoundedCornerShape(
        topStart = if (isUser) 16.dp else 4.dp,
        topEnd = if (isUser) 4.dp else 16.dp,
        bottomEnd = 16.dp,
        bottomStart = 16.dp
    )

    Row(
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .background(color = bubbleColor, shape = bubbleShape)
                .then(
                    if (msg.isError && onRetry != null) Modifier.clickable { onRetry() }
                    else Modifier
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                msg.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = when {
                        isUser -> Color.White
                        msg.isError -> errorColor
                        else -> colors.onSurface
                    },
                    lineHeight = 20.sp
                )
            )
            if (msg.isError && onRetry != null) {
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        tint = errorColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        "Tap to retry",
                        modifier = Modifier.padding(start = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = errorColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ChatBubblePreview() {
    ChatBubble(
        msg = ChatMessage(
            text = "Hello", role = ChatRole.USER
        )
    )
}