package com.example.smartaiexpensetracker.core.data

import kotlinx.serialization.Serializable


@Serializable
data class CategoryResponse (
    val uid:String,
    val name:String
)