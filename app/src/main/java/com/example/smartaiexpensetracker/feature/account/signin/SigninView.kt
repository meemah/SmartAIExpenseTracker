package com.example.smartaiexpensetracker.feature.account.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.smartaiexpensetracker.core.composables.AppButton
import com.example.smartaiexpensetracker.core.composables.AppTextField
import com.example.smartaiexpensetracker.core.composables.TextFieldType
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.navigation.Routes
import com.example.smartaiexpensetracker.core.util.UiState
import com.example.smartaiexpensetracker.feature.account.composables.AccountHeaderText
import com.example.smartaiexpensetracker.feature.account.composables.AuthRichText

@Composable
fun SignInView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.submitState) {
        when (uiState.submitState) {
            is UiState.Success -> {
                navController.navigate(Routes.MAIN) {
                    popUpTo(Routes.SIGN_IN) { inclusive = true }
                }
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (uiState.submitState as UiState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }
    Scaffold(
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            AccountHeaderText(
                title = "Welcome Back!", subTitle = "Please enter your details to sign in"
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .glassCard()
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
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
                    hintText = "*******",
                    textFieldType = TextFieldType.PASSWORD,
                    errorMessage = uiState.password.error
                )
                AppButton(
                    modifier = Modifier.padding(top = 10.dp),
                    buttonTitle = "Sign In",
                    isLoading = uiState.submitState is UiState.Loading
                ) {
                    viewModel.signIn()
                }
            }
            AuthRichText(
                primaryText = "Don't have an account?",
                clickableText = "Create an account",
                tag = "SIGNUP"
            ) {
                navController.navigate(Routes.SIGN_UP)
            }
        }
    }
}
