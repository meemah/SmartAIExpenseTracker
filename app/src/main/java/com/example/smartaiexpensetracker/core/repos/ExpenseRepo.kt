package com.example.smartaiexpensetracker.core.repos

import com.example.smartaiexpensetracker.core.data.ExpenseResponse
import com.example.smartaiexpensetracker.core.util.ApiResultWrapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import io.ktor.client.HttpClient
import javax.inject.Inject

interface ExpenseRepo {
    suspend fun getExpenses() : ApiResponse<ApiResultWrapper<List<ExpenseResponse>>>
}

class ExpenseRepoImpl @Inject constructor(
    val httpClient: HttpClient
) : ExpenseRepo{
    override suspend fun getExpenses(): ApiResponse<ApiResultWrapper<List<ExpenseResponse>>> {
       return httpClient.getApiResponse<ApiResultWrapper<List<ExpenseResponse>>>("expenses/")
    }

}