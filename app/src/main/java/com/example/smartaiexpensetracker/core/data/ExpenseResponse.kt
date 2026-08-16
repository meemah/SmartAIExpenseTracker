package com.example.smartaiexpensetracker.core.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseResponse(
    val amount: String,

    val category: String,

    val createdAt: String,

    val description: String,

    val uid: String
)