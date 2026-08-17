package com.example.smartaiexpensetracker.core.repos

import com.example.smartaiexpensetracker.core.data.ChatInsight
import com.example.smartaiexpensetracker.core.data.ChatResponse
import com.example.smartaiexpensetracker.core.util.ApiResultWrapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.ktor.postApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import javax.inject.Inject

interface ChatRepo {
    suspend fun chat(message: String): ApiResponse<ApiResultWrapper<ChatResponse>>

    suspend fun getInsight(): ApiResponse<ApiResultWrapper<ChatInsight>>
}


class ChatRepoImpl @Inject constructor(
    val httpClient: HttpClient
) : ChatRepo {
    override suspend fun chat(message: String): ApiResponse<ApiResultWrapper<ChatResponse>> {
        return httpClient.postApiResponse<ApiResultWrapper<ChatResponse>>("chat/") {
            setBody(mapOf("message" to message))
        }
    }

    override suspend fun getInsight(): ApiResponse<ApiResultWrapper<ChatInsight>> {
        return httpClient.getApiResponse("chat/insight/")
    }

}