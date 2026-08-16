package com.example.smartaiexpensetracker.core.util

import android.util.Patterns


object Validators {
    fun email(value: String): String? = when {
        value.isBlank() -> "Email is required"
        !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> "Invalid email"
        else -> null
    }

    fun required(value: String, fieldName: String = "Field"): String? =
        if (value.isBlank()) "$fieldName is required" else null

    fun minLength(value: String, min: Int, fieldName: String = "Field"): String? =
        if (value.length < min) "$fieldName must be at least $min characters" else null

    fun password(value: String, fieldName: String? = null): String? =
        required(value, fieldName ?: "Password") ?: minLength(value, 6, "Password")

    fun confirmPassword(password: String, confirmPassword: String): String? = when {
        required(
            confirmPassword,
            fieldName = "Confirm Password"
        ) != null -> "Confirm Password is required"

        confirmPassword != password -> "Passwords do not match"
        else -> null
    }

}