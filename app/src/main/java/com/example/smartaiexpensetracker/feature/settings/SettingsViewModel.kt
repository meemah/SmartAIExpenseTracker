package com.example.smartaiexpensetracker.feature.settings

import androidx.lifecycle.ViewModel
import com.example.smartaiexpensetracker.core.manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    val tokenManager: TokenManager
) : ViewModel() {

    fun logout() {
        tokenManager.clearTokens()

    }
}