package com.example.smartaiexpensetracker.core.repos

import com.example.smartaiexpensetracker.core.data.BudgetBulkRequest
import com.example.smartaiexpensetracker.core.data.BudgetResponse
import com.example.smartaiexpensetracker.core.data.BudgetSummary
import com.example.smartaiexpensetracker.core.util.ApiResultWrapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.ktor.postApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import javax.inject.Inject
import kotlin.collections.mapOf

interface BudgetRepo {
    suspend fun getBudgetSummary(categoryUid: String? = null): ApiResponse<ApiResultWrapper<BudgetSummary>>

    suspend fun getBudget(): ApiResponse<ApiResultWrapper<List<BudgetResponse>>>

    suspend fun setBudget(budgets: BudgetBulkRequest): ApiResponse<Any>
}

class BudgetRepoImpl @Inject constructor(
    val httpClient: HttpClient
) : BudgetRepo {
    override suspend fun getBudgetSummary(categoryUid: String?): ApiResponse<ApiResultWrapper<BudgetSummary>> {
        return httpClient.getApiResponse("expenses/summary") {
            categoryUid?.let {
                parameter("category_uid", it)
            }
        }
    }

    override suspend fun getBudget(): ApiResponse<ApiResultWrapper<List<BudgetResponse>>> {
        return httpClient.getApiResponse("budget/")
    }

    override suspend fun setBudget(budgets: BudgetBulkRequest): ApiResponse<Any> {
        return httpClient.postApiResponse("budget/bulk") {
            setBody(budgets)
        }
    }

}