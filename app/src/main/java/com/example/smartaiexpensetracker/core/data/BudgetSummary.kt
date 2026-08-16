package com.example.smartaiexpensetracker.core.data


import kotlinx.serialization.Serializable

@Serializable
data class BudgetSummary(
    val budget: Double,
    val byCategory: List<BudgetSummaryCategory>,
    val count: Double,
    val remaining: Double,
    val spent: Double
)@Serializable
data class BudgetSummaryCategory(
    val budget: Double,
    val categoryName: String,
    val categoryUid: String,
    val remaining: Double,
    val spent: Double
)