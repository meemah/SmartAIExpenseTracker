package com.example.smartaiexpensetracker.core.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)