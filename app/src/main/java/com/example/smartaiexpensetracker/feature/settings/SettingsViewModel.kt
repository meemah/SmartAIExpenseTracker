package com.example.smartaiexpensetracker.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartaiexpensetracker.core.data.ThemeMode
import com.example.smartaiexpensetracker.core.data.User
import com.example.smartaiexpensetracker.core.manager.SessionManager
import com.example.smartaiexpensetracker.core.manager.ThemeDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    val sessionManager: SessionManager,
    val themeDataStore: ThemeDataStore
) : ViewModel() {

    fun logout() {
        sessionManager.clearSession()

    }

    val user: User? = sessionManager.getUser()

    fun updateTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            themeDataStore.setTheme(themeMode)
        }


    }
}