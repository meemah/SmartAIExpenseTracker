package com.example.smartaiexpensetracker.feature.budget

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartaiexpensetracker.core.data.BudgetSummary
import com.example.smartaiexpensetracker.core.repos.BudgetRepo
import com.example.smartaiexpensetracker.core.util.UiState
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetTrackerViewModel @Inject constructor(
    private val budgetRepo: BudgetRepo
) : ViewModel() {



    private val _budgetSummary = MutableStateFlow<UiState<BudgetSummary>>(UiState.Loading)

    val budgetSummary: StateFlow<UiState<BudgetSummary>> = _budgetSummary
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun getBudgetSummary() {
        viewModelScope.launch {
            if (_budgetSummary.value !is UiState.Success) {
                _budgetSummary.value = UiState.Loading
            }
            budgetRepo.getBudgetSummary().onSuccess {
                _budgetSummary.value = UiState.Success(data = data.data)
            }.onException {
                if (_budgetSummary.value !is UiState.Success) {
                    _budgetSummary.value = UiState.Error(message = throwable.message ?: "Couldn't get budget")
                }
            }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                budgetRepo.getBudgetSummary().onSuccess {
                    _budgetSummary.value = UiState.Success(data = data.data)
                }
            } catch (_: Exception) {
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}