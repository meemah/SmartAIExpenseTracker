package com.example.smartaiexpensetracker.core.data

import kotlinx.serialization.Serializable

@Serializable
data class BudgetResponse(
    val categoryUid: String,
    val categoryName: String,
    val amount: Double? = 0.0
)

@Serializable
data class BudgetRequest(
    val categoryUid: String,
    val amount: Double
)

@Serializable
data class BudgetBulkRequest(
    val budgets: List<BudgetRequest>
)
