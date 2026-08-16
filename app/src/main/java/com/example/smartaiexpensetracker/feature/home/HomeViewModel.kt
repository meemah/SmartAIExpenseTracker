package com.example.smartaiexpensetracker.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartaiexpensetracker.core.data.BudgetSummary
import com.example.smartaiexpensetracker.core.data.ChatInsight
import com.example.smartaiexpensetracker.core.repos.BudgetRepo
import com.example.smartaiexpensetracker.core.repos.ChatRepo
import com.example.smartaiexpensetracker.core.util.UiState
import com.example.smartaiexpensetracker.core.util.unwrap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chatRepo: ChatRepo,
    private val budgetRepo: BudgetRepo
) : ViewModel() {

    private val _budgetSummary =
        MutableStateFlow<UiState<BudgetSummary>>(UiState.Loading)
    val budgetSummary: StateFlow<UiState<BudgetSummary>> = _budgetSummary

    private val _chatInsight =
        MutableStateFlow<UiState<ChatInsight>>(UiState.Loading)
    val chatInsight: StateFlow<UiState<ChatInsight>> = _chatInsight

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun getBudgetSummary() {
        viewModelScope.launch {
            if (_budgetSummary.value !is UiState.Success) {
                _budgetSummary.value = UiState.Loading
            }
            try {
                val budget = budgetRepo.getBudgetSummary().unwrap()
                _budgetSummary.value = UiState.Success(budget)
            } catch (e: Exception) {
                if (_budgetSummary.value !is UiState.Success) {
                    _budgetSummary.value = UiState.Error(e.message ?: "Couldn't get budget summary")
                }
            }
        }
    }

    fun getChatInsight() {
        viewModelScope.launch {
            if (_chatInsight.value !is UiState.Success) {
                _chatInsight.value = UiState.Loading
            }
            try {
                val insight = chatRepo.getInsight().unwrap()
                _chatInsight.value = UiState.Success(insight)
            } catch (e: Exception) {
                if (_chatInsight.value !is UiState.Success) {
                    _chatInsight.value = UiState.Error(e.message ?: "Couldn't get chat insight")
                }
            }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                val budget = async { budgetRepo.getBudgetSummary().unwrap() }
                val insight = async { chatRepo.getInsight().unwrap() }
                _budgetSummary.value = UiState.Success(budget.await())
                _chatInsight.value = UiState.Success(insight.await())
            } catch (_: Exception) {
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}