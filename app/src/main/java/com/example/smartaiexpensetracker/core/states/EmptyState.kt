package com.example.smartaiexpensetracker.core.states

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartaiexpensetracker.core.theme.customColors


@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.AutoMirrored.Filled.List,
            contentDescription = null,
            tint = MaterialTheme.customColors.surface,
            modifier = modifier
                .size(50.dp)
                .background(
                    color =
                        MaterialTheme.customColors.onSurface,
                    shape = RoundedCornerShape(8.dp),

                    )
                .padding(10.dp)
        )
        Text(
            "No Items Yet",
            color = MaterialTheme.customColors.onSurfaceVariant,
            modifier = modifier.padding(top = 10.dp)
        )
    }
}