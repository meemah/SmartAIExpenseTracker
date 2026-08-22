package com.example.smartaiexpensetracker.feature.chat

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val role: ChatRole,
    val isError: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ChatRole {
    USER, ASSISTANT
}