package com.example.smartaiexpensetracker.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.smartaiexpensetracker.core.navigation.Routes
import com.example.smartaiexpensetracker.feature.MainView
import com.example.smartaiexpensetracker.feature.chat.ChatView
import com.example.smartaiexpensetracker.feature.settings.BudgetSetupView
import com.example.smartaiexpensetracker.feature.settings.SettingsView

fun NavGraphBuilder.mainRoutes(navController: NavController) {
    composable(Routes.MAIN) { MainView(navController = navController) }
    composable(Routes.CHAT) {
        ChatView()
    }
    composable(Routes.SETTINGS) { SettingsView(navController = navController) }
    composable(Routes.BUDGET_SETUP) { BudgetSetupView(navController = navController) }

}