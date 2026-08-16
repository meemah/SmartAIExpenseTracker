package com.example.smartaiexpensetracker.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.theme.CustomThemeColor


@Composable
fun AIInsightCard(
    modifier: Modifier = Modifier, typography: Typography, colors: CustomThemeColor, insight: String
) {
    Row(
        modifier = Modifier
            .glassCard()
            .height(200.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp)
                .background(colors.onPrimaryContainer)

        )
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                Icons.Default.SmartToy,
                contentDescription = null,
                tint = colors.onSurfaceVariant,
                modifier = Modifier
                    .background(
                        color = colors.onPrimaryContainer, shape = CircleShape
                    )
                    .padding(10.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),

                ) {
                Text(
                    stringResource(R.string.ai_insight).uppercase(),
                    style = typography.bodyMedium.copy(
                        color = colors.onPrimaryContainer, fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    insight, overflow = TextOverflow.Ellipsis, style = typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        color = colors.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 26.sp,
                    )
                )
            }
        }
    }

}