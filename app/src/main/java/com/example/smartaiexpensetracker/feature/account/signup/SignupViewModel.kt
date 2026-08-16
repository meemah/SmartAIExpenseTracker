package com.example.smartaiexpensetracker.feature.account.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartaiexpensetracker.core.repos.AuthRepo
import com.example.smartaiexpensetracker.core.util.FieldState
import com.example.smartaiexpensetracker.core.util.UiState
import com.example.smartaiexpensetracker.core.util.Validators
import com.example.smartaiexpensetracker.feature.account.signin.SignInUiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
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
class SignupViewModel @Inject constructor(
    private val authRepo: AuthRepo

) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    fun signUp() {
        val state = _uiState.value
        val emailError = Validators.email(state.email.value)
        val firstNameError = Validators.required(state.firstName.value, fieldName = "First Name")
        val lastNameError = Validators.required(state.lastName.value, fieldName = "Last Name")
        val passwordError = Validators.password(state.password.value)
        val confirmPasswordError =
            Validators.confirmPassword(state.password.value, state.confirmPassword.value)
        if (emailError != null || firstNameError != null || lastNameError != null || passwordError != null || confirmPasswordError != null) {
            _uiState.update {
                it.copy(
                    email = it.email.copy(error = emailError),
                    firstName = it.firstName.copy(error = firstNameError),
                    lastName = it.lastName.copy(error = lastNameError),
                    password = it.password.copy(error = passwordError),
                    confirmPassword = it.confirmPassword.copy(error = confirmPasswordError),
                )
            }
            return
        }

        viewModelScope.launch {
            authRepo.signUp(
                email = state.email.value,
                firstName = state.firstName.value,
                lastName = state.lastName.value,
                password = state.confirmPassword.value
            ).onSuccess {
                _uiState.update {
                    it.copy(
                        userState = UiState.Success(data.data.user)
                    )
                }
            }.onException {
                _uiState.update {
                    it.copy(
                        userState = UiState.Error(
                            throwable.message ?: "Network error"
                        )
                    )
                }
            }

        }

    }


    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(
                email = FieldState(value)
            )
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                password = FieldState(value)
            )
        }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                confirmPassword = FieldState(value)
            )
        }
    }

    fun onFirstNameChange(value: String) {
        _uiState.update {
            it.copy(
                firstName = FieldState(value)
            )
        }
    }


    fun onLastNameChange(value: String) {
        _uiState.update {
            it.copy(
                lastName = FieldState(value)
            )
        }
    }
}