package com.example.smartaiexpensetracker.core.di

import android.util.Log
import com.example.smartaiexpensetracker.core.data.RefreshTokenResponse
import com.example.smartaiexpensetracker.core.manager.SessionManager
import com.example.smartaiexpensetracker.core.util.ApiException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideHttpClient(sessionManager: SessionManager): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        namingStrategy = JsonNamingStrategy.SnakeCase
                    }
                )
            }
            install(Logging) {
                level = LogLevel.BODY
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorClient", message)
                    }
                }
            }
            defaultRequest {
                url("http://10.0.2.2:8000/api/v1/")
                contentType(ContentType.Application.Json)
            }
            HttpResponseValidator {
                validateResponse { response ->
                    if (!response.status.isSuccess()) {
                        val message = try {
                            val body = response.bodyAsText()
                            val json = Json.parseToJsonElement(body).jsonObject
                            json["message"]?.jsonPrimitive?.content
                                ?: json["detail"]?.jsonPrimitive?.content
                                ?: json.values.firstNotNullOfOrNull { it.jsonPrimitive.contentOrNull }
                                ?: "Something went wrong"
                        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
                            throw e
                        } catch (e: Exception) {
                            "Something went wrong"
                        }
                        throw ApiException(response.status.value, message)
                    }
                }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = sessionManager.getAccessToken()
                        val refreshToken = sessionManager.getRefreshToken()
                        if (accessToken != null && refreshToken != null) {
                            BearerTokens(accessToken, refreshToken)
                        } else null
                    }
                    refreshTokens {
                        val refreshToken = sessionManager.getRefreshToken();
                        if (refreshToken != null) {
                            try {
                                val response = client.post("users/refresh") {
                                    headers {
                                        append("Authorization", "Bearer $refreshToken")
                                    }
                                }
                                val body = response.body<RefreshTokenResponse>()
                                sessionManager.saveTokens(body.accessToken, body.refreshToken)
                                BearerTokens(body.accessToken, body.refreshToken)

                            } catch (_: Exception) {
                                sessionManager.clearSession()
                                null
                            }
                        }
                        return@refreshTokens null
                    }
                }

            }
        }
    }
}