package com.example.smartaiexpensetracker.feature.home

import com.example.smartaiexpensetracker.core.states.ErrorState
import com.example.smartaiexpensetracker.core.states.LoadingState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.data.BudgetSummaryCategory
import com.example.smartaiexpensetracker.core.data.CategoryType
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.states.EmptyState
import com.example.smartaiexpensetracker.core.theme.customColors
import com.example.smartaiexpensetracker.core.util.UiState
import com.example.smartaiexpensetracker.core.util.formatNaira
import com.example.smartaiexpensetracker.feature.home.composables.AIInsightCard
import com.example.smartaiexpensetracker.feature.home.composables.RemainingBudgetCard
import com.example.smartaiexpensetracker.feature.home.composables.TotalAmountSpentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(isActive) {
        if (isActive) {
            homeViewModel.getBudgetSummary()
            homeViewModel.getChatInsight()
        }
    }
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.customColors
    val budgetSummaryState by homeViewModel.budgetSummary.collectAsStateWithLifecycle()
    val budgetSummaryData = (budgetSummaryState as? UiState.Success)?.data
    val chatInsightState by homeViewModel.chatInsight.collectAsStateWithLifecycle()
    val chatInsight = (chatInsightState as? UiState.Success)?.data
    val isRefreshing by homeViewModel.isRefreshing.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { homeViewModel.refresh() },
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.padding(20.dp)
        ) {
            item() {
                TotalAmountSpentCard(
                    modifier = modifier,
                    typography = typography,
                    colors = colors,
                    amountSpent = budgetSummaryData?.spent,
                    budget = budgetSummaryData?.budget
                )
                RemainingBudgetCard(
                    modifier = modifier,
                    typography = typography,
                    colors = colors,
                    budgetSummaryData?.remaining
                )
                if (!chatInsight?.insight.isNullOrEmpty()) AIInsightCard(
                    modifier = modifier,
                    typography = typography,
                    colors = colors,
                    chatInsight.insight
                )
                Text(
                    stringResource(R.string.spending_categories),
                    modifier = Modifier.padding(vertical = 10.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 22.sp, fontWeight = FontWeight.Bold,
                        color = colors.onSurface,
                    )
                )
                when (budgetSummaryState) {
                    is UiState.Error -> ErrorState(
                        message = (budgetSummaryState as UiState.Error).message,
                        onRetry = {
                            homeViewModel.getBudgetSummary()
                        })

                    is UiState.Loading -> LoadingState(
                        message = stringResource(R.string.fetching_spending_categories)
                    )

                    is UiState.Success -> {
                        if (budgetSummaryData?.byCategory.isNullOrEmpty()) {
                            EmptyState()
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(
                                budgetSummaryData?.byCategory ?: emptyList<BudgetSummaryCategory>()
                            ) {
                                val category =
                                    CategoryType.fromName(it.categoryName)
                                Column(
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier
                                        .glassCard()
                                        .size(width = 150.dp, height = 120.dp)
                                        .padding(vertical = 12.dp, horizontal = 15.dp)

                                ) {
                                    Icon(
                                        category.icon,
                                        contentDescription = category.title,
                                        tint = category.color
                                    )
                                    Text(
                                        category.title, style = typography.titleMedium.copy(
                                            letterSpacing = 0.8.sp,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onSurfaceVariant,
                                        )
                                    )
                                    Text(
                                        it.spent.formatNaira(), style = typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onSurface,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

