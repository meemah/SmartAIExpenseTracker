package com.example.smartaiexpensetracker.feature.account.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartaiexpensetracker.core.data.AuthResponse
import com.example.smartaiexpensetracker.core.manager.SessionManager
import com.example.smartaiexpensetracker.core.repos.AuthRepo
import com.example.smartaiexpensetracker.core.util.FieldState
import com.example.smartaiexpensetracker.core.util.UiState
import com.example.smartaiexpensetracker.core.util.Validators
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepo,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun signIn() {
        val state = _uiState.value
        val emailError = Validators.email(state.email.value)
        val passwordError = Validators.password(state.password.value)

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    email = it.email.copy(error = emailError),
                    password = it.password.copy(error = passwordError)
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(submitState = UiState.Loading) }

            authRepository.signIn(
                state.email.value, password = state.password.value

            ).onSuccess {
                val authResponse: AuthResponse = data.data
                sessionManager.saveTokens(authResponse.accessToken, authResponse.refreshToken)
                sessionManager.saveUser(authResponse.user)
                _uiState.update { it.copy(submitState = UiState.Success(authResponse.user)) }
            }.onException {
                _uiState.update {
                    it.copy(
                        submitState = UiState.Error(
                            throwable.message ?: "Network error"
                        )
                    )
                }
            }

        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = FieldState(value)) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = FieldState(value)) }
    }

}