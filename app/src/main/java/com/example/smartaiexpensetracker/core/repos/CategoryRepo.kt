package com.example.smartaiexpensetracker.core.repos

import com.example.smartaiexpensetracker.core.data.CategoryResponse
import com.example.smartaiexpensetracker.core.util.ApiResultWrapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import io.ktor.client.HttpClient
import javax.inject.Inject

interface CategoryRepo {
    suspend fun getAllCategories(): ApiResponse<ApiResultWrapper<List<CategoryResponse>>>
}

class CategoryRepoImpl @Inject constructor(
    val httpClient: HttpClient
) : CategoryRepo {
    override suspend fun getAllCategories(): ApiResponse<ApiResultWrapper<List<CategoryResponse>>> {
       return  httpClient.getApiResponse<ApiResultWrapper<List<CategoryResponse>>>("categories/")
    }

}