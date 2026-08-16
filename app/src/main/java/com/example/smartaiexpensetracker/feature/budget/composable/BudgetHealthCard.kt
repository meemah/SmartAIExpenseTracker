package com.example.smartaiexpensetracker.feature.budget.composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import java.time.YearMonth


@Composable
fun BudgetHealthCard(
    modifier: Modifier = Modifier,
    colors: CustomThemeColor,
    budgetSpent: Double?,
    budgetTotal: Double?,
) {
    val budgetRemaining = ( budgetTotal ?: 0.0)  - (budgetSpent ?:0.0)
    Box(
        modifier = modifier
            .glassCard()
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Row {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        stringResource(R.string.monthly_spending).uppercase(),
                        letterSpacing = 1.6.sp,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colors.onSurfaceVariant, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        stringResource(R.string.budget_health),
                        modifier = Modifier.padding(bottom = 6.dp, top = 2.dp),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = colors.onSurface,
                            letterSpacing = 1.sp,
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            (budgetSpent ?: 0.0).formatNaira(),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = colors.onSurface,
                            )
                        )
                        Text(
                            " /${(budgetTotal ?: 0.0).formatNaira()}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = colors.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
                Text(
                    stringResource(R.string.percent_spent, "${if ((budgetTotal ?: 0.0) > 0.0) ((budgetSpent ?: 0.0) / budgetTotal!! * 100).toInt() else 0}%"),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onSurface,
                    ),
                    modifier = Modifier
                        .background(
                            color = colors.onPrimaryContainer, shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth()
                    .height(height = 18.dp),
                color = colors.onPrimaryContainer,
                strokeCap = StrokeCap.Round,
                progress = {
                    if ((budgetTotal ?: 0.0) > 0.0) ((budgetSpent ?: 0.0) / budgetTotal!!).toFloat().coerceIn(0f, 1f) else 0f
                })
            val spentPercent = if ((budgetTotal ?: 0.0) > 0.0) (budgetSpent ?: 0.0) / budgetTotal!! else 0.0
            val dayOfMonth = java.time.LocalDate.now().dayOfMonth
            val daysInMonth = YearMonth.now().lengthOfMonth()
            val monthProgress = dayOfMonth.toDouble() / daysInMonth

            val budgetStatus = when {
                spentPercent >= 1.0 -> BudgetStatus.OVER_BUDGET
                spentPercent > monthProgress + 0.1 -> BudgetStatus.AT_RISK
                else -> BudgetStatus.ON_TRACK
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    budgetStatus.icon,
                    contentDescription = null,
                    tint = budgetStatus.color,
                    modifier = Modifier.size(16.dp, 16.dp)
                )
                Text(
                    budgetStatus.label.uppercase(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = budgetStatus.color, fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    stringResource(R.string.percent_utilized, "${if ((budgetTotal ?: 0.0) > 0.0) ((budgetSpent ?: 0.0) / budgetTotal!! * 100).toInt() else 0}%"),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colors.onSurfaceVariant, fontWeight = FontWeight.Bold
                    )
                )
            }

            Row(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .background(
                            color = colors.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        stringResource(R.string.remaining),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colors.onSurfaceVariant,
                        )
                    )
                    Text(
                        budgetRemaining.formatNaira(),
                        modifier = Modifier.padding(top = 4.dp),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            color = colors.surface,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
                Box(
                    modifier = Modifier.width(width = 12.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .background(
                            color = colors.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(10.dp)
                ) {
                    Text(
                        stringResource(R.string.days_left_label),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colors.onSurfaceVariant,
                        )
                    )
                    Text(
                        stringResource(R.string.days_count,      YearMonth.now().lengthOfMonth() - java.time.LocalDate.now().dayOfMonth),
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colors.surface,
                            fontSize = 16.sp, fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }

        }
    }
}

enum class BudgetStatus(val label: String, val icon: ImageVector, val color: Color) {
    ON_TRACK("On Track", Icons.Default.CheckCircle, Color(0xFF22C55E)),
    AT_RISK("At Risk", Icons.Default.Warning, Color(0xFFF59E0B)),
    OVER_BUDGET("Over Budget", Icons.Default.Error, Color(0xFFEF4444))
}