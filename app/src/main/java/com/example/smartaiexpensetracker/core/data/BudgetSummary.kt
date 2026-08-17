package com.example.smartaiexpensetracker.core.data


import kotlinx.serialization.Serializable

@Serializable
data class BudgetSummary(
    val budget: Double=0.0,
    val byCategory: List<BudgetSummaryCategory>,
    val count: Double,
    val remaining:  Double=0.0,
    val spent: Double
)@Serializable
data class BudgetSummaryCategory(
    val budget: Double? = null,
    val categoryName: String,
    val categoryUid: String,
    val remaining: Double? = null,
    val spent: Double = 0.0
)