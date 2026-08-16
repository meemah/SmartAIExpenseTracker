package com.example.smartaiexpensetracker.core.data

import kotlinx.serialization.Serializable

@Serializable
class User(
    val firstName: String,
    val lastName: String,
    val uid: String,
    val email: String

)
