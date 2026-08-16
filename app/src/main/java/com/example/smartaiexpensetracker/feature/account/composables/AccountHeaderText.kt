package com.example.smartaiexpensetracker.feature.account.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartaiexpensetracker.core.theme.customColors

@Composable
fun AccountHeaderText(title: String, subTitle: String) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.customColors
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            style = typography.headlineSmall.copy(
                color = colors.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
            )
        )
        Text(
            subTitle,
            modifier = Modifier.padding(top = 10.dp),
            textAlign = TextAlign.Center,
            style = typography.bodyMedium.copy(
                color = colors.onSurface,
            )
        )
    }

}