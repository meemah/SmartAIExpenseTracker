package com.example.smartaiexpensetracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.smartaiexpensetracker.core.manager.TokenManager
import com.example.smartaiexpensetracker.core.navigation.graph.authRoutes
import com.example.smartaiexpensetracker.core.navigation.graph.mainRoutes

@Composable
fun AppNavigation(tokenManager: TokenManager) {
    val  navController = rememberNavController()
    val startDestination = if(tokenManager.getAccessToken()!=null) Routes.MAIN else Routes.SIGN_IN

    NavHost(navController = navController,startDestination=startDestination){
        authRoutes(navController)
        mainRoutes(navController)
    }
}