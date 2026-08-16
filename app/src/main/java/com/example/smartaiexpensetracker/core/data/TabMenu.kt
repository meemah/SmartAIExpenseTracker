package com.example.smartaiexpensetracker.core.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.ui.graphics.vector.ImageVector

enum class TabMenu(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    Home("Home", Icons.Default.Home, "home"),
    History("History", Icons.Default.Receipt, "history"),
    Budget("Budget", Icons.Default.Wallet, "wallet"),
//    Assistant("Assistant", Icons.Default.SmartToy, "assistant")
}