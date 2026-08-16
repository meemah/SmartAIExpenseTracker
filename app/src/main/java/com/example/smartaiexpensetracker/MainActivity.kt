package com.example.smartaiexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smartaiexpensetracker.core.manager.TokenManager
import com.example.smartaiexpensetracker.core.navigation.AppNavigation
import com.example.smartaiexpensetracker.core.theme.SmartAIExpenseTrackerTheme
import com.example.smartaiexpensetracker.feature.MainView
import com.example.smartaiexpensetracker.feature.account.signin.SignInView
import com.example.smartaiexpensetracker.feature.account.signup.SignUpView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartAIExpenseTrackerTheme {
                AppNavigation(tokenManager)
            }
        }
    }
}
