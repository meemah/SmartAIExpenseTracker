package com.example.smartaiexpensetracker.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartaiexpensetracker.core.theme.customColors

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    buttonTitle: String,
    isLoading: Boolean = false,
    backgroundColor: Color = MaterialTheme.customColors.onSecondaryContainer,
    onClick: () -> Unit,

) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
            .clickable(enabled = !isLoading, onClick = onClick)
            .padding(vertical = 15.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                buttonTitle, style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                )
            )
        }

    }
}