package com.example.smartaiexpensetracker.core.data
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val showOnNonTabSection: Boolean = true,

)


object CategoryData {
    val homeCategories = listOf(
        Category("Food", Icons.Default.Restaurant, Color(0xFFFFE0B2)),
        Category("Transport", Icons.Default.DirectionsCar, Color(0xFFBBDEFB)),
        Category("Rent", Icons.Default.Home, Color(0xFFD1C4E9)),
        Category("Utilities", Icons.Default.Bolt, Color(0xFFC8E6C9)),
        Category("Shopping", Icons.Default.ShoppingCart, Color(0xFFFFCDD2)),
        Category("Health", Icons.Default.MedicalServices, Color(0xFFB2EBF2)),
        Category("Entertainment", Icons.Default.Movie, Color(0xFFF8BBD0)),
        Category("Other", Icons.Default.Category, Color(0xFFE0E0E0))
    )
}