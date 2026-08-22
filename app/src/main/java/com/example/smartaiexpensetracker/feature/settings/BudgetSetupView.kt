package com.example.smartaiexpensetracker.feature.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.smartaiexpensetracker.core.composables.AppButton
import com.example.smartaiexpensetracker.core.composables.AppTextField
import com.example.smartaiexpensetracker.core.composables.TextFieldType
import com.example.smartaiexpensetracker.core.data.BudgetResponse
import com.example.smartaiexpensetracker.core.data.CategoryType
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.states.ErrorState
import com.example.smartaiexpensetracker.core.states.LoadingState
import com.example.smartaiexpensetracker.core.theme.customColors
import com.example.smartaiexpensetracker.core.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSetupView(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: BudgetSetupViewModel = hiltViewModel()

) {
    val colors = MaterialTheme.customColors
    val categoriesState by viewModel.budgetsResponse.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                MaterialTheme.customColors.background
            ), navigationIcon = {
                IconButton(onClick = {
                    navController?.popBackStack()
                }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = null)
                }
            }, title = {
                Text("Budget Setup")
            })
        }) { innerPadding ->

        when (categoriesState) {
            is UiState.Error -> ErrorState(message = (categoriesState as UiState.Error).message)
            is UiState.Loading -> LoadingState(message = "Fetching categories")
            is UiState.Success<List<BudgetResponse>> -> {
                val categories = (categoriesState as UiState.Success).data
                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .glassCard()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                   if (viewModel.sameBudgetForAll )"Same budget for all categories" else "Custom budget for each category",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold, color = colors.onSurface
                                    )
                                )
                                Text(
                                    "Toggle to set individual category limits",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = colors.slate
                                    )
                                )
                            }
                            Switch(
                                checked = viewModel.sameBudgetForAll,
                                onCheckedChange = { viewModel.sameBudgetForAll = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = colors.onPrimaryContainer,
                                    uncheckedThumbColor = colors.slate,
                                    uncheckedTrackColor = colors.surfaceContainer
                                )
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        if (viewModel.sameBudgetForAll) {
                            AppTextField(
                                label = "Budget per category",
                                value = viewModel.totalBudgetAmount,
                                onValueChange = {
                                    viewModel.totalBudgetAmount = it
                                },
                                textFieldType = TextFieldType.NUMBER_ONLY,
                                fontWeight = FontWeight.Bold,
                                hintText = "Enter a monthly budget"
                            )
                        } else {
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(categories) { item ->
                                    val category = CategoryType.fromName(item.categoryName)
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(end = 12.dp)
                                                .background(
                                                    color = category.color.copy(alpha = 0.15f),
                                                    shape = RoundedCornerShape(12.dp)
                                                )
                                                .size(44.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                category.icon,
                                                contentDescription = item.categoryName,
                                                tint = category.color
                                            )
                                        }
                                        Text(
                                            item.categoryName.capitalize(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = colors.onSurface
                                            ),
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(end = 8.dp)
                                        )
                                        AppTextField(
                                            value = viewModel.amountForCategories[item.categoryUid] ?: "",
                                            onValueChange = { value ->
                                                viewModel.updateAmount(item, value)
                                            },
                                            hintText = "0",
                                            bottonPadding = 0.0,
                                            modifier = Modifier.weight(0.8f),
                                            textFieldType = TextFieldType.NUMBER_ONLY,
                                            textAlign = TextAlign.End,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    HorizontalDivider(
                                        color = colors.slate.copy(alpha = 0.12f),
                                        thickness = 0.5.dp
                                    )
                                }
                                item {
                                    HorizontalDivider(
                                        color = colors.onPrimaryContainer,
                                        thickness = 1.dp,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 14.dp)
                                    ) {
                                        Text(
                                            "Total Budget",
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = colors.onSurface
                                            ),
                                            modifier = Modifier.weight(1f)
                                        )
                                        val total = viewModel.amountForCategories.values.sumOf { it.toDoubleOrNull() ?: 0.0 }
                                        Text(
                                            if (total % 1.0 == 0.0) total.toLong().toString() else "%.2f".format(total),
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = colors.onPrimaryContainer
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        AppButton(
                            buttonTitle = "Save Budget",
                            isLoading = viewModel.isSaving,
                            onClick = {
                                viewModel.setBudget { message ->
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            })


                    }
                }
            }

        }

    }
}

@Preview
@Composable
fun BudgetSetupPreview(modifier: Modifier = Modifier) {
    BudgetSetupView()
}