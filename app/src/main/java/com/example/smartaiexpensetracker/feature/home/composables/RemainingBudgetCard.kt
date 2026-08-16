package com.example.smartaiexpensetracker.feature.home.composables

import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.theme.CustomThemeColor
import com.example.smartaiexpensetracker.core.util.formatNaira
import java.time.YearMonth

@Composable
fun RemainingBudgetCard(
    modifier: Modifier = Modifier,
    typography: Typography,
    colors: CustomThemeColor,
    remainingBudget: Double?,

    ) {
    Column(
        modifier = modifier
            .padding(vertical = 15.dp)
            .background(
                color = colors.surface, shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)

        ) {
            Icon(
                Icons.Outlined.AccountBalanceWallet,
                contentDescription = null,
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(
                            alpha = 0.03f
                        ), shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
            )
            Text(
                stringResource(R.string.remaining_budget).uppercase(),
                modifier = Modifier.padding(vertical = 8.dp),
                style = typography.titleMedium.copy(
                    letterSpacing = 0.8.sp,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onPrimaryContainer.copy(
                        alpha = 0.8f
                    )
                )
            )
            Text(
                (remainingBudget ?: 0.0).formatNaira(),
                style = typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colors.onPrimaryContainer,
                    fontSize = 26.sp
                )
            )
        }
        HorizontalDivider(
            color = colors.onPrimaryContainer.copy(alpha = 0.1f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            val daysLeft = remember {
                YearMonth.now().lengthOfMonth() - java.time.LocalDate.now().dayOfMonth
            }
            Text(
                stringResource(
                    R.string.days_left,
                    daysLeft
                ), style = typography.bodyMedium.copy(
                    letterSpacing = 0.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onPrimaryContainer.copy(
                        alpha = 0.8f
                    )
                )
            )
            Text(
                stringResource(R.string.plan_high_savings),
                style = typography.bodyMedium.copy(
                    letterSpacing = 0.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onPrimaryContainer.copy(
                        alpha = 0.8f
                    )
                )
            )
        }
    }
}