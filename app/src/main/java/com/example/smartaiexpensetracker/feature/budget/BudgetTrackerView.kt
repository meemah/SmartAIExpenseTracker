package com.example.smartaiexpensetracker.feature.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.data.CategoryType
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.states.EmptyState
import com.example.smartaiexpensetracker.core.states.ErrorState
import com.example.smartaiexpensetracker.core.states.LoadingState
import com.example.smartaiexpensetracker.core.theme.customColors
import com.example.smartaiexpensetracker.core.util.UiState
import com.example.smartaiexpensetracker.core.util.formatNaira
import com.example.smartaiexpensetracker.feature.budget.composable.BudgetHealthCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetTrackerView(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    budgetTrackerViewModel: BudgetTrackerViewModel = hiltViewModel()
) {
    LaunchedEffect(isActive) {
        if (isActive) budgetTrackerViewModel.getBudgetSummary()
    }
    val colors = MaterialTheme.customColors
    val budgetSummaryState by budgetTrackerViewModel.budgetSummary.collectAsStateWithLifecycle()
    val budgetSummaryData = (budgetSummaryState as? UiState.Success)?.data
    val isRefreshing by budgetTrackerViewModel.isRefreshing.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { budgetTrackerViewModel.refresh() },
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                BudgetHealthCard(
                    modifier = Modifier,
                    colors = colors,
                    budgetSpent = budgetSummaryData?.spent,
                    budgetTotal = budgetSummaryData?.budget
                )
            }

            item {
                Text(
                    stringResource(R.string.category_spending),
                    modifier = Modifier.padding(top = 3.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 22.sp, fontWeight = FontWeight.Bold,
                        color = colors.onSurface,
                    )
                )
            }

            when (budgetSummaryState) {
                is UiState.Error -> item {
                    ErrorState(
                        message = (budgetSummaryState as UiState.Error).message,
                        onRetry = {
                            budgetTrackerViewModel.getBudgetSummary()
                        })
                }

                is UiState.Loading -> item {
                    LoadingState(
                        message = stringResource(R.string.fetching_spending_categories)
                    )
                }

                is UiState.Success -> {
                    if (budgetSummaryData?.byCategory.isNullOrEmpty()) {
                        item { EmptyState() }
                    }
                    items(budgetSummaryData?.byCategory.orEmpty()) {
                        val category = CategoryType.fromName(it.categoryName)
                        Column(
                            modifier = Modifier
                                .glassCard()
                                .padding(13.dp)
                        ) {
                            Row {
                                Icon(
                                    category.icon,
                                    contentDescription = it.categoryName,
                                    tint = category.color,
                                    modifier = Modifier
                                        .background(
                                            color = category.color.copy(
                                                alpha = 0.3f
                                            ), shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(10.dp)
                                )
                                Column(
                                    modifier = Modifier.padding(
                                        start = 10.dp
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            category.title,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = colors.onSurface,
                                            )
                                        )
                                        Text(
                                            it.spent.formatNaira(),
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = colors.onSurface,
                                            )
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            stringResource(
                                                R.string.amount_remaining,
                                                ((it.budget ?: 0.0) - it.spent).coerceAtLeast(0.0).formatNaira()
                                            ),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = colors.onSurfaceVariant,
                                            )
                                        )
                                        Text(
                                            stringResource(
                                                R.string.of_amount_remaining,
                                                (it.budget ?: 0.0).formatNaira()
                                            ),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = colors.onSurfaceVariant,
                                            )
                                        )
                                    }

                                    LinearProgressIndicator(
                                        color = category.color,
                                        strokeCap = StrokeCap.Round,
                                        modifier = Modifier
                                            .padding(top = 15.dp)
                                            .height(20.dp)
                                            .fillMaxWidth(),
                                        trackColor = colors.background,
                                        progress = {
                                            val b = it.budget ?: 0.0
                                            if (b > 0.0) (it.spent / b).toFloat()
                                                .coerceIn(0f, 1f) else 0f
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
