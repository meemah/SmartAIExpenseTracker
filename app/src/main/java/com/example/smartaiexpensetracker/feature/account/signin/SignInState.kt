package com.example.smartaiexpensetracker.feature.account.signin
import com.example.smartaiexpensetracker.core.data.User
import com.example.smartaiexpensetracker.core.util.FieldState
import com.example.smartaiexpensetracker.core.util.UiState

data class SignInUiState(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val submitState: UiState<User>? = null
)