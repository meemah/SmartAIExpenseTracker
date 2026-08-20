package com.example.smartaiexpensetracker.core.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryType(val title: String, val icon: ImageVector, val color: Color) {
    FOOD("Food", Icons.Default.Restaurant, Color(0xFFFF9800)),
    TRANSPORT("Transport", Icons.Default.DirectionsCar, Color(0xFF2196F3)),
    RENT("Rent", Icons.Default.Home, Color(0xFF7C4DFF)),
    UTILITIES("Utilities", Icons.Default.Bolt, Color(0xFF4CAF50)),
    SHOPPING("Shopping", Icons.Default.ShoppingCart, Color(0xFFE91E63)),
    HEALTH("Health", Icons.Default.MedicalServices, Color(0xFF00BCD4)),
    ENTERTAINMENT("Entertainment", Icons.Default.Movie, Color(0xFFEC407A)),
    SUBSCRIPTIONS("Subscriptions", Icons.Default.Autorenew, Color(0xFF8D6E63)),
    EDUCATION("Education", Icons.Default.School, Color(0xFF5C6BC0)),
    TRAVEL("Travel", Icons.Default.Flight, Color(0xFFFFA726)),
    OTHER("Other", Icons.Default.Category, Color(0xFF9E9E9E));

    companion object {
        fun fromName(name: String): CategoryType =
            entries.find { it.title.equals(name, ignoreCase = true) }?:CategoryType.OTHER
    }
}