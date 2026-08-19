package com.example.smartaiexpensetracker.core.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryType(val title: String, val icon: ImageVector, val color: Color) {
    FOOD("Food", Icons.Default.Restaurant, Color(0xFFFFE0B2)),
    TRANSPORT("Transport", Icons.Default.DirectionsCar, Color(0xFFBBDEFB)),
    RENT("Rent", Icons.Default.Home, Color(0xFFD1C4E9)),
    UTILITIES("Utilities", Icons.Default.Bolt, Color(0xFFC8E6C9)),
    SHOPPING("Shopping", Icons.Default.ShoppingCart, Color(0xFFFFCDD2)),
    HEALTH("Health", Icons.Default.MedicalServices, Color(0xFFB2EBF2)),
    ENTERTAINMENT("Entertainment", Icons.Default.Movie, Color(0xFFF8BBD0)),
    SUBSCRIPTIONS("Subscriptions", Icons.Default.Autorenew, Color(0xFFD7CCC8)),
    EDUCATION("Education", Icons.Default.School, Color(0xFFC5CAE9)),
    TRAVEL("Travel", Icons.Default.Flight, Color(0xFFFFECB3)),
    OTHER("Other", Icons.Default.Category, Color(0xFFE0E0E0));

    companion object {
        fun fromName(name: String): CategoryType =
            entries.find { it.title.equals(name, ignoreCase = true) }?:CategoryType.OTHER
    }
}