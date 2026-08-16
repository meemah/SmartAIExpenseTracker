package com.example.smartaiexpensetracker.core.util

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.statusCode
import kotlinx.serialization.Serializable


@Serializable
data class ApiResultWrapper<T>(
    val message: String,
    val data: T,
    val success: Boolean
)


fun <T> ApiResponse<ApiResultWrapper<T>>.unwrap(): T {
    return when (this) {
        is ApiResponse.Success -> data.data
        is ApiResponse.Failure.Error -> throw ApiException(statusCode.code, "Request failed")
        is ApiResponse.Failure.Exception -> throw throwable
    }
}