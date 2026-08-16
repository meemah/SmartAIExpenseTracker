package com.example.smartaiexpensetracker.feature.account.signup

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.composables.AppButton
import com.example.smartaiexpensetracker.core.composables.AppTextField
import com.example.smartaiexpensetracker.core.composables.TextFieldType
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.navigation.Routes
import com.example.smartaiexpensetracker.core.util.UiState

import com.example.smartaiexpensetracker.core.util.Validators.confirmPassword
import com.example.smartaiexpensetracker.feature.account.composables.AccountHeaderText
import com.example.smartaiexpensetracker.feature.account.composables.AuthRichText

@Composable
fun SignUpView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(uiState.userState) {
        when (uiState.userState) {
            is UiState.Success -> {
                navController.navigate(Routes.MAIN) {
                    popUpTo(Routes.SIGN_IN) { inclusive = true }
                }
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (uiState.userState as UiState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }
    Scaffold(
        modifier = modifier, content = { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                AccountHeaderText(
                    title = stringResource(R.string.monitor_your_finance),
                    subTitle = stringResource(R.string.track_finance_description),
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .glassCard()
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    AppTextField(
                        label = stringResource(R.string.first_name),
                        value = uiState.firstName.value,
                        onValueChange = viewModel::onFirstNameChange,
                        hintText = "John",
                        errorMessage = uiState.firstName.error

                    )
                    AppTextField(
                        label = stringResource(R.string.last_name),
                        value = uiState.lastName.value,
                        onValueChange = viewModel::onLastNameChange,
                        hintText = "Doe",
                        errorMessage = uiState.lastName.error
                    )
                    AppTextField(
                        label = "Email Address",
                        value = uiState.email.value,
                        onValueChange = viewModel::onEmailChange,
                        hintText = "john.doe@finance.com",
                        textFieldType = TextFieldType.EMAIL,
                        errorMessage = uiState.email.error
                    )
                    AppTextField(
                        label = "Password",
                        value = uiState.password.value,
                        onValueChange = viewModel::onPasswordChange,
                        hintText = "********",
                        textFieldType = TextFieldType.PASSWORD,
                        errorMessage = uiState.password.error
                    )
                    AppTextField(
                        label = "Confirm Password",
                        value = uiState.confirmPassword.value,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        hintText = "********",
                        textFieldType = TextFieldType.PASSWORD,
                        errorMessage = uiState.confirmPassword.error
                    )
                    AppButton(
                        modifier = Modifier.padding(top = 10.dp),
                        buttonTitle = "Sign up",
                        isLoading = uiState.userState is UiState.Loading,
                        onClick = {
                            viewModel.signUp()
                        })
                }

                AuthRichText(
                    primaryText = "Already have an account? ",
                    clickableText = "Log In",
                    tag = "LOGIN"
                ) {
                    navController.navigate(Routes.SIGN_IN)
                }
            }
        })
}



