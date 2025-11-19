package com.ekddigital.careshpere.core.network

import android.content.Context
import android.content.SharedPreferences
import com.ekddigital.careshpere.core.models.*
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Network client for API communication
 * Android equivalent of NetworkClient.swift
 */
class NetworkClient private constructor(context: Context) {
    
    companion object {
        @Volatile
        private var INSTANCE: NetworkClient? = null
        
        fun getInstance(context: Context): NetworkClient {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NetworkClient(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences("caresphere_auth", Context.MODE_PRIVATE)
    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()
    
    private val baseUrl = "https://caresphere.ekddigital.com"
    
    val isAuthenticated: Boolean
        get() = getAccessToken() != null
    
    // MARK: - Token Management
    
    fun setAuthToken(accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply()
    }
    
    fun getAccessToken(): String? = prefs.getString("access_token", null)
    
    fun getRefreshToken(): String? = prefs.getString("refresh_token", null)
    
    fun clearAuthTokens() {
        prefs.edit()
            .remove("access_token")
            .remove("refresh_token")
            .apply()
    }
    
    // MARK: - HTTP Methods
    
    suspend fun <T> request(
        endpoint: String,
        method: HttpMethod = HttpMethod.GET,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        responseType: Class<T>
    ): T = withContext(Dispatchers.IO) {
        executeRequest(endpoint, method, body, headers, responseType)
    }
    
    suspend inline fun <reified T> request(
        endpoint: String,
        method: HttpMethod = HttpMethod.GET,
        body: Any? = null,
        headers: Map<String, String> = emptyMap()
    ): T = request(endpoint, method, body, headers, T::class.java)
    
    private fun <T> executeRequest(
        endpoint: String,
        method: HttpMethod,
        body: Any?,
        headers: Map<String, String>,
        responseType: Class<T>
    ): T {
        val url = "$baseUrl$endpoint"
        
        println("[NetworkClient] Making request to: $url")
        println("[NetworkClient] Method: ${method.name}")
        if (body != null) {
            val bodyJson = gson.toJson(body)
            println("[NetworkClient] Request Body: $bodyJson")
        }
        
        val requestBuilder = Request.Builder()
            .url(url)
        
        // Add headers
        headers.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }
        
        // Add request body if present
        if (body != null && method != HttpMethod.GET) {
            val json = gson.toJson(body)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            requestBuilder.method(method.name, json.toRequestBody(mediaType))
        } else {
            requestBuilder.method(method.name, null)
        }
        
        val request = requestBuilder.build()
        
        try {
            println("[NetworkClient] Making HTTP request to: $url")
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            
            println("[NetworkClient] Response Code: ${response.code}")
            println("[NetworkClient] Response Body: $responseBody")
            
            return when {
                response.isSuccessful -> {
                    if (responseType == Unit::class.java) {
                        Unit as T
                    } else {
                        try {
                            // First try to parse as wrapped response
                            val wrappedResponse = gson.fromJson(responseBody, APISuccessResponse::class.java)
                            if (wrappedResponse.success && wrappedResponse.data != null) {
                                // Convert the data part to the expected type
                                val dataJson = gson.toJson(wrappedResponse.data)
                                gson.fromJson(dataJson, responseType)
                            } else {
                                throw APIError.ServerError(response.code, wrappedResponse.error ?: "Unknown error")
                            }
                        } catch (e: Exception) {
                            // If parsing as wrapped fails, try direct parsing
                            println("Failed to parse wrapped response, trying direct: ${e.message}")
                            println("Response body: $responseBody")
                            gson.fromJson(responseBody, responseType)
                        }
                    }
                }
                response.code == 401 -> throw APIError.Unauthorized
                response.code == 403 -> throw APIError.Forbidden
                response.code in 400..499 -> {
                    val errorBody = response.body?.string() ?: ""
                    try {
                        val apiResponse = gson.fromJson(errorBody, APIResponse::class.java)
                        if (apiResponse.errors != null) {
                            throw APIError.ValidationError(apiResponse.errors)
                        } else {
                            throw APIError.ServerError(response.code, apiResponse.message ?: "Client error")
                        }
                    } catch (e: Exception) {
                        throw APIError.ServerError(response.code, "Request failed")
                    }
                }
                else -> throw APIError.ServerError(response.code, "Server error")
            }
        } catch (e: IOException) {
            throw APIError.NetworkError("Network connection failed: ${e.message}")
        } catch (e: APIError) {
            throw e
        } catch (e: Exception) {
            throw APIError.Unknown(e)
        }
    }
    
    private fun <T> handleSuccessResponse(responseBody: String, responseType: Class<T>): T {
        return try {
            // First try to decode as APISuccessResponse (with wrapper)
            val wrappedResponse = gson.fromJson(responseBody, APISuccessResponse::class.java)
            if (wrappedResponse.success && wrappedResponse.data != null) {
                // Convert the data JsonElement to the expected type
                gson.fromJson(wrappedResponse.data, responseType)
            } else {
                throw APIError.ServerError(400, wrappedResponse.error ?: "API returned success=false")
            }
        } catch (e: Exception) {
            // Fallback: try to decode T directly (in case API doesn't use wrapper)
            try {
                gson.fromJson(responseBody, responseType)
            } catch (fallbackError: Exception) {
                throw APIError.ServerError(500, "Failed to decode response: ${e.message}")
            }
        }
    }
    
    // MARK: - Auth Interceptor
    
    private inner class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val token = getAccessToken()
            
            val authenticatedRequest = if (token != null) {
                request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                request
            }
            
            return chain.proceed(authenticatedRequest)
        }
    }
}

enum class HttpMethod {
    GET, POST, PUT, DELETE, PATCH
}

// MARK: - API Endpoints

object Endpoints {
    object Auth {
        const val login = "/auth/login"
        const val register = "/auth/register"
        const val logout = "/auth/logout"
        const val profile = "/auth/profile"
        const val refreshToken = "/auth/refresh"
    }
    
    object Members {
        const val list = "/members"
        const val search = "/members/search"
        fun get(id: String) = "/members/$id"
        const val create = "/members"
        fun update(id: String) = "/members/$id"
        fun delete(id: String) = "/members/$id"
        fun notes(memberId: String) = "/members/$memberId/notes"
        fun activities(memberId: String) = "/members/$memberId/activities"
    }
    
    object Messages {
        const val list = "/messages"
        const val create = "/messages"
        fun send(id: String) = "/messages/$id/send"
        fun analytics(id: String) = "/messages/$id/analytics"
    }
    
    object Templates {
        const val list = "/templates"
        const val create = "/templates"
        fun get(id: String) = "/templates/$id"
        fun update(id: String) = "/templates/$id"
        fun delete(id: String) = "/templates/$id"
    }
    
    object Settings {
        const val senderResolved = "/settings/senders/resolved"
        fun senderList(scope: String, referenceId: String? = null): String {
            return if (referenceId != null) {
                "/settings/senders?scope=$scope&reference_id=$referenceId"
            } else {
                "/settings/senders?scope=$scope"
            }
        }
        fun senderUpdate(scope: String, referenceId: String? = null): String {
            return if (referenceId != null) {
                "/settings/senders?scope=$scope&reference_id=$referenceId"
            } else {
                "/settings/senders?scope=$scope"
            }
        }
        fun senderDelete(scope: String, referenceId: String? = null): String {
            return if (referenceId != null) {
                "/settings/senders?scope=$scope&reference_id=$referenceId"
            } else {
                "/settings/senders?scope=$scope"
            }
        }
    }
}

// MARK: - API Error Handling

sealed class APIError : Exception() {
    object Unauthorized : APIError()
    object Forbidden : APIError()
    data class ValidationError(val errors: Map<String, List<String>>) : APIError()
    data class ServerError(val statusCode: Int, override val message: String) : APIError()
    data class NetworkError(override val message: String) : APIError()
    data class Unknown(val originalException: Exception) : APIError()
}

// MARK: - API Response

data class APIResponse(
    val message: String? = null,
    val errors: Map<String, List<String>>? = null
)