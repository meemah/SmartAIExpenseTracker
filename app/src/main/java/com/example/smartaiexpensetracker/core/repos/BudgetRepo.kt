package com.example.smartaiexpensetracker.core.repos

import com.example.smartaiexpensetracker.core.data.BudgetSummary
import com.example.smartaiexpensetracker.core.util.ApiResultWrapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.ktor.postApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import javax.inject.Inject

interface BudgetRepo {
    suspend fun getBudgetSummary(categoryUid: String? = null): ApiResponse<ApiResultWrapper<BudgetSummary>>
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

}