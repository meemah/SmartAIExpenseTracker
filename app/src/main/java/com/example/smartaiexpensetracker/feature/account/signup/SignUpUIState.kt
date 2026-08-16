package com.example.smartaiexpensetracker.feature.account.signup

import com.example.smartaiexpensetracker.core.data.User
import com.example.smartaiexpensetracker.core.util.FieldState
import com.example.smartaiexpensetracker.core.util.UiState

data class SignUpUIState (
    val email: FieldState = FieldState(),
    val firstName: FieldState = FieldState(),
    val lastName: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val userState: UiState<User> ? = null
)