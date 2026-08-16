package com.example.smartaiexpensetracker.core.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.smartaiexpensetracker.core.theme.customColors

@Composable
fun Modifier.glassCard(): Modifier {
    val colors = MaterialTheme.customColors
    return this
        .clip(RoundedCornerShape(20.dp))
        .border(
            1.dp,
            colors.glassBorder,
            RoundedCornerShape(20.dp)
        )
        .background(
            colors.glassBackground.copy(alpha = 0.6f)
        )
}
