package com.example.smartaiexpensetracker.feature.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.theme.CustomThemeColor
import com.example.smartaiexpensetracker.core.util.formatNaira

@Composable
fun TotalAmountSpentCard(
    modifier: Modifier = Modifier,
    typography: Typography,
    colors: CustomThemeColor,
    amountSpent: Double?,
    budget: Double?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .glassCard()
            .padding(
                vertical = 30.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.total_spent_this_month).uppercase(),
            style = typography.headlineSmall.copy(
                color = colors.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.6.sp
            )
        )
        Text(
            (amountSpent ?: 0.0).formatNaira(),
            modifier = Modifier.padding(vertical = 4.dp),
            style = typography.bodyLarge.copy(
                color = colors.onSurface,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp
            )
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(150.dp)
                    .width(150.dp),
                color = colors.surface,
                strokeCap = StrokeCap.Round,
                strokeWidth = 10.dp,
                trackColor = colors.background,
                progress = {
                    if ((budget ?: 0.0) > 0.0) ((amountSpent ?: 0.0) / budget!!).toFloat().coerceIn(0f, 1f) else 0f
                })
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "${if ((budget ?: 0.0) > 0.0) ((amountSpent ?: 0.0) / budget!! * 100).toInt() else 0}%",
                    style = typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = colors.onSurface
                    ),
                )
                Text(
                    stringResource(R.string.used).uppercase(),
                    style = typography.headlineSmall.copy(
                        color = colors.onSurfaceVariant,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.6.sp,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}