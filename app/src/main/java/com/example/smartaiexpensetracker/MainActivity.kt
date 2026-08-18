package com.example.smartaiexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartaiexpensetracker.core.data.ThemeMode
import com.example.smartaiexpensetracker.core.manager.SessionManager
import com.example.smartaiexpensetracker.core.manager.ThemeDataStore
import com.example.smartaiexpensetracker.core.navigation.AppNavigation
import com.example.smartaiexpensetracker.core.theme.SmartAIExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager
    @Inject
    lateinit var themeDataStore: ThemeDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by themeDataStore.themeMode.collectAsStateWithLifecycle(
                initialValue = ThemeMode.SYSTEM
            )
            val isDarkMode = when (themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }
            SmartAIExpenseTrackerTheme(isDarkMode) {
                AppNavigation(sessionManager)
            }
        }
    }
}
