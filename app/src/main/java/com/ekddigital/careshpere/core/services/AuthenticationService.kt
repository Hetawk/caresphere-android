package com.ekddigital.careshpere.core.services

import android.content.Context
import com.ekddigital.careshpere.core.models.*
import com.ekddigital.careshpere.core.network.APIError
import com.ekddigital.careshpere.core.network.Endpoints
import com.ekddigital.careshpere.core.network.HttpMethod
import com.ekddigital.careshpere.core.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Service for handling user authentication and session management
 * Android equivalent of AuthenticationService.swift
 */
class AuthenticationService private constructor(context: Context) {
    
    companion object {
        @Volatile
        private var INSTANCE: AuthenticationService? = null
        
        fun getInstance(context: Context): AuthenticationService {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthenticationService(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    
    private val networkClient = NetworkClient.getInstance(context)
    
    // State management using StateFlow for reactive UI updates
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<APIError?>(null)
    val error: StateFlow<APIError?> = _error.asStateFlow()
    
    val isAuthenticated: Boolean
        get() = _currentUser.value != null && networkClient.isAuthenticated
    
    init {
        // Check if user is already authenticated on initialization
        if (networkClient.isAuthenticated) {
            // We could load current user here, but for now just check tokens
        }
    }
    
    // MARK: - Authentication Methods
    
    suspend fun login(email: String, password: String, rememberMe: Boolean = true): Boolean {
        println("[AuthService] Starting login for: $email")
        _isLoading.value = true
        _error.value = null
        
        return try {
            val request = LoginRequest(
                email = email.lowercase().trim(),
                password = password,
                rememberMe = rememberMe
            )
            
            println("[AuthService] Making login request to API")
            
            val response: LoginResponse = networkClient.request(
                endpoint = Endpoints.Auth.login,
                method = HttpMethod.POST,
                body = request
            )
            
            // Store authentication tokens
            networkClient.setAuthToken(response.accessToken, response.refreshToken)
            
            // Set current user
            _currentUser.value = response.user
            
            _isLoading.value = false
            true
            
        } catch (apiError: APIError) {
            _error.value = apiError
            _isLoading.value = false
            false
        } catch (error: Exception) {
            _error.value = APIError.Unknown(error)
            _isLoading.value = false
            false
        }
    }
    
    suspend fun register(
        email: String,
        password: String,
        fullName: String,
        displayName: String? = null
    ): Boolean {
        _isLoading.value = true
        _error.value = null
        
        return try {
            val request = RegisterRequest(
                email = email.lowercase().trim(),
                password = password,
                fullName = fullName.trim(),
                displayName = displayName?.trim()
            )
            
            val response: LoginResponse = networkClient.request(
                endpoint = Endpoints.Auth.register,
                method = HttpMethod.POST,
                body = request
            )
            
            // Store authentication tokens
            networkClient.setAuthToken(response.accessToken, response.refreshToken)
            
            // Set current user
            _currentUser.value = response.user
            
            _isLoading.value = false
            true
            
        } catch (apiError: APIError) {
            _error.value = apiError
            _isLoading.value = false
            false
        } catch (error: Exception) {
            _error.value = APIError.Unknown(error)
            _isLoading.value = false
            false
        }
    }
    
    suspend fun logout() {
        _isLoading.value = true
        
        try {
            // Call logout endpoint (fire and forget)
            networkClient.request<Unit>(
                endpoint = Endpoints.Auth.logout,
                method = HttpMethod.POST
            )
        } catch (e: Exception) {
            // We can log this error but don't block logout flow
            println("Logout request failed: ${e.message}")
        }
        
        // Clear local state
        networkClient.clearAuthTokens()
        _currentUser.value = null
        _isLoading.value = false
    }
    
    suspend fun loadCurrentUser(): Boolean {
        if (!networkClient.isAuthenticated) {
            _currentUser.value = null
            return false
        }
        
        return try {
            val user: User = networkClient.request(endpoint = Endpoints.Auth.profile)
            _currentUser.value = user
            true
        } catch (apiError: APIError) {
            // Only clear auth when tokens are invalid
            when (apiError) {
                is APIError.Unauthorized, is APIError.Forbidden -> {
                    networkClient.clearAuthTokens()
                    _currentUser.value = null
                }
                else -> {
                    _error.value = apiError
                }
            }
            false
        } catch (error: Exception) {
            _error.value = APIError.Unknown(error)
            false
        }
    }
    
    // MARK: - Permission Checking
    
    fun hasPermission(permission: (UserPermissions) -> Boolean): Boolean {
        val user = _currentUser.value ?: return false
        return permission(user.role.permissions)
    }
    
    fun requiresPermission(permission: (UserPermissions) -> Boolean) {
        if (!hasPermission(permission)) {
            throw APIError.Forbidden
        }
    }
    
    // MARK: - Error Management
    
    fun clearError() {
        _error.value = null
    }
}