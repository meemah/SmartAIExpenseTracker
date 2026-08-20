package com.example.smartaiexpensetracker.feature.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartaiexpensetracker.core.data.BudgetSummary
import com.example.smartaiexpensetracker.core.data.ExpenseResponse
import com.example.smartaiexpensetracker.core.repos.BudgetRepo
import com.example.smartaiexpensetracker.core.repos.ExpenseRepo
import com.example.smartaiexpensetracker.core.util.UiState
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val expenseRepo: ExpenseRepo
) : ViewModel() {
    private val _expenseTransactions =
        MutableStateFlow<UiState<List<ExpenseResponse>>>(UiState.Loading)

    val expenseTransactions: StateFlow<UiState<List<ExpenseResponse>>> = _expenseTransactions

    private val _categories = mutableSetOf<String>()
    val categories: Set<String> get() = _categories
    var selectedCategory by mutableStateOf<String?>(null)
        private set

    fun selectCategory(category: String?) {
        selectedCategory = category
    }


    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun getTransactions() {
        viewModelScope.launch {
            if (_expenseTransactions.value !is UiState.Success) {
                _expenseTransactions.value = UiState.Loading
            }
            expenseRepo.getExpenses().onSuccess {
                data.data.forEach {
                    _categories.add(it.category)
                }
                selectCategory(_categories.firstOrNull())
                _expenseTransactions.value = UiState.Success(data.data)
            }.onException {
                if (_expenseTransactions.value !is UiState.Success) {
                    _expenseTransactions.value =
                        UiState.Error(throwable.message ?: "Couldn't get transactions")
                }
            }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                expenseRepo.getExpenses().onSuccess {
                    _categories.clear()
                    data.data.forEach {
                        _categories.add(it.category)
                    }
                    selectCategory(_categories.firstOrNull())
                    _expenseTransactions.value = UiState.Success(data.data)
                }
            } catch (_: Exception) {
            } finally {
                _isRefreshing.value = false
            }
        }
    }


    fun formatDateHeader(dateString: String): String {
        val date = LocalDate.parse(dateString.substringBefore("T"))
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return when (date) {
            today -> "Today"
            yesterday -> "Yesterday"
            else -> date.format(
                DateTimeFormatter.ofPattern("MMM dd, yyyy")
            )

        }
    }
}