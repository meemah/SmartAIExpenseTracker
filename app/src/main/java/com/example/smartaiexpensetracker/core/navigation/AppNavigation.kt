package com.example.smartaiexpensetracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.smartaiexpensetracker.core.manager.SessionManager
import com.example.smartaiexpensetracker.core.navigation.graph.authRoutes
import com.example.smartaiexpensetracker.core.navigation.graph.mainRoutes

@Composable
fun AppNavigation(sessionManager: SessionManager) {
    val  navController = rememberNavController()
    val startDestination = if(sessionManager.getAccessToken()!=null) Routes.MAIN else Routes.SIGN_IN

    NavHost(navController = navController,startDestination=startDestination){
        authRoutes(navController)
        mainRoutes(navController)
    }
}