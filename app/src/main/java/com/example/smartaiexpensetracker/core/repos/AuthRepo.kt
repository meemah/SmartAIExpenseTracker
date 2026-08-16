package com.example.smartaiexpensetracker.core.repos

import com.example.smartaiexpensetracker.core.data.AuthResponse
import com.example.smartaiexpensetracker.core.util.ApiResultWrapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.postApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import javax.inject.Inject


interface AuthRepo {
    suspend fun signIn(email: String, password: String): ApiResponse<ApiResultWrapper<AuthResponse>>

    suspend fun signUp(
        email: String, password: String, firstName: String, lastName: String
    ): ApiResponse<ApiResultWrapper<AuthResponse>>
}


class AuthRepoImpl @Inject constructor(
    private val httpClient: HttpClient
)

    : AuthRepo {
    override suspend fun signIn(email: String, password: String): ApiResponse<ApiResultWrapper<AuthResponse>>{

        return httpClient.postApiResponse<ApiResultWrapper<AuthResponse>>("users/login") {
            setBody(mapOf("email" to email, "password" to password))
        }
    }

    override suspend fun signUp(
        email: String, password: String, firstName: String, lastName: String
    ): ApiResponse<ApiResultWrapper<AuthResponse>> {
        return httpClient.postApiResponse<ApiResultWrapper<AuthResponse>>("users/signup") {
            setBody(
                mapOf(
                    "email" to email,
                    "password" to password,
                    "first_name" to firstName,
                    "last_name" to lastName
                )
            )
        }
    }

}