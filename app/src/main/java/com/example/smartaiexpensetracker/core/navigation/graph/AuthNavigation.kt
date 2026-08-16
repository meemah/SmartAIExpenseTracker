package com.example.smartaiexpensetracker.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.smartaiexpensetracker.core.navigation.Routes
import com.example.smartaiexpensetracker.feature.account.signin.SignInView
import com.example.smartaiexpensetracker.feature.account.signup.SignUpView

fun NavGraphBuilder.authRoutes(navController: NavController) {
    composable(Routes.SIGN_IN) { SignInView(navController = navController) }
    composable(Routes.SIGN_UP) { SignUpView(navController = navController) }
}