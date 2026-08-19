package com.example.smartaiexpensetracker.feature.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.data.CategoryType
import com.example.smartaiexpensetracker.core.data.ExpenseResponse
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.states.EmptyState
import com.example.smartaiexpensetracker.core.states.ErrorState
import com.example.smartaiexpensetracker.core.states.LoadingState
import com.example.smartaiexpensetracker.core.theme.customColors
import com.example.smartaiexpensetracker.core.util.UiState
import com.example.smartaiexpensetracker.core.util.formatNaira


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    LaunchedEffect(isActive) {
        if (isActive) viewModel.getTransactions()
    }
    val colors = MaterialTheme.customColors
    val transactionState by viewModel.expenseTransactions.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()


    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() },
        modifier = modifier
    ) {
        when (transactionState) {
            is UiState.Error -> ErrorState(
                message = (transactionState as UiState.Error).message,
                onRetry = {
                    viewModel.getTransactions()
                })

            is UiState.Loading -> LoadingState(message = "Fetching your transactions")
            is UiState.Success<List<ExpenseResponse>> -> {
                val transactions: List<ExpenseResponse> =
                    ((transactionState as UiState.Success).data)
                if (transactions.isEmpty()) {
                    EmptyState()
                }

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 10.dp)
                    ) {
                        items(viewModel.categories.toList()) {
                            val isSelected = it == viewModel.selectedCategory
                            val backgroundColor =
                                if (isSelected) colors.onPrimaryContainer else colors.glassBackground

                            Text(
                                it.capitalize(),
                                modifier = Modifier
                                    .clickable(onClick = {
                                        viewModel.selectCategory(it)
                                    })
                                    .background(
                                        color = backgroundColor,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onSurface,
                                )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.height(height = 10.dp)
                    )
                    Text(
                        stringResource(R.string.today),
                        modifier = Modifier.padding(bottom = 5.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onSurfaceVariant
                        ),
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(transactions.filter {
                            it.category.equals(
                                viewModel.selectedCategory,
                                ignoreCase = true
                            )
                        }) {
                            val category =
                                CategoryType.fromName(it.category)

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .glassCard()
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                Icon(
                                    category.icon,
                                    contentDescription = category.title,
                                    tint = category.color,
                                    modifier = Modifier
                                        .background(
                                            color = category.color.copy(
                                                alpha = 0.1f
                                            ), shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(10.dp)
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 20.dp)
                                ) {
                                    Text(
                                        it.description,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onSurfaceVariant
                                        )
                                    )
                                    Text(
                                        category.title.capitalize(),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onSurface
                                        )
                                    )
                                }
                                Column {
                                    Text(
                                        it.amount.toDoubleOrNull().formatNaira(),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onSurfaceVariant
                                        ),
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


@Preview
@Composable
fun TransactionsScreenPreview() {
    TransactionsScreen()
}


