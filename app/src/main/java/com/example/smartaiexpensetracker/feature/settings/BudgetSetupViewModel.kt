package com.example.smartaiexpensetracker.feature.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartaiexpensetracker.core.data.BudgetBulkRequest
import com.example.smartaiexpensetracker.core.data.BudgetRequest
import com.example.smartaiexpensetracker.core.data.BudgetResponse
import com.example.smartaiexpensetracker.core.data.CategoryResponse
import com.example.smartaiexpensetracker.core.repos.BudgetRepo
import com.example.smartaiexpensetracker.core.repos.CategoryRepo
import com.example.smartaiexpensetracker.core.util.UiState
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetSetupViewModel @Inject constructor(
    val budgetRepo: BudgetRepo
) : ViewModel() {
    private val _budgetsResponse =
        MutableStateFlow<UiState<List<BudgetResponse>>>(UiState.Loading)

    val budgetsResponse: StateFlow<UiState<List<BudgetResponse>>> = _budgetsResponse;


    var isSaving by mutableStateOf(false)
        private set


    fun getAllCategories() {
        viewModelScope.launch {
            _budgetsResponse.value = UiState.Loading
            budgetRepo.getBudget().onSuccess {
                data.data.forEach { budgetResponse ->
                    val amount = budgetResponse.amount ?: 0.0
                    amountForCategories[budgetResponse.categoryUid] =
                        if (amount % 1.0 == 0.0) amount.toLong().toString() else amount.toString()
                }
                _budgetsResponse.value = UiState.Success(data.data)
            }.onException {
                _budgetsResponse.value =
                    UiState.Error(throwable.message ?: "Error fetching categories")
            }
        }
    }

    init {
        getAllCategories()
    }

    var amountForCategories = mutableStateMapOf<String, String>()

    fun updateAmount(budget: BudgetResponse, amount: String) {
        amountForCategories[budget.categoryUid] = amount
    }

    var sameBudgetForAll by
    mutableStateOf(true)

    var totalBudgetAmount by
    mutableStateOf("")


    fun setBudget(onResult: (String) -> Unit) {
        viewModelScope.launch {
            isSaving = true

            if (sameBudgetForAll) {
                amountForCategories.forEach { (uid, _) ->
                    amountForCategories[uid] = totalBudgetAmount
                }
            }

            val budgets = amountForCategories.map { (uid, amount) ->
                BudgetRequest(categoryUid = uid, amount = amount.toDoubleOrNull() ?: 0.0)
            }

            budgetRepo.setBudget(BudgetBulkRequest(budgets = budgets)).onSuccess {
                onResult("Budget updated successfully")
            }.onException {
                onResult(throwable.message ?: "Error saving budget")
            }
            isSaving = false
        }
    }

}