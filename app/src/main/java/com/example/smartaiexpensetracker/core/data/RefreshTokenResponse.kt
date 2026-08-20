package com.example.smartaiexpensetracker.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(

    val accessToken: String,
    val refreshToken: String
)