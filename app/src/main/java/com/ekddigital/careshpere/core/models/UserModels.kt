package com.ekddigital.careshpere.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * User Management Models - Android equivalent of UserModels.swift
 */

/**
 * User model representing authenticated users in the system
 */
@Serializable
data class User(
    val id: String,
    val email: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    val role: UserRole,
    val status: UserStatus,
    @SerialName("email_verified") val emailVerified: Boolean,
    @SerialName("last_login_at") val lastLoginAt: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
) {
    // Computed properties for backward compatibility
    val firstName: String
        get() = fullName.split(" ").firstOrNull() ?: fullName
    
    val lastName: String
        get() {
            val components = fullName.split(" ")
            return if (components.size > 1) components.drop(1).joinToString(" ") else ""
        }
    
    val effectiveDisplayName: String
        get() = displayName ?: fullName
    
    companion object {
        val preview = User(
            id = "preview-user-id",
            email = "demo@caresphere.com",
            fullName = "Demo User",
            displayName = "Demo",
            avatarUrl = null,
            role = UserRole.ADMIN,
            status = UserStatus.ACTIVE,
            emailVerified = true,
            lastLoginAt = null,
            createdAt = "2025-11-18T00:00:00",
            updatedAt = "2025-11-18T00:00:00"
        )
    }
}

/**
 * User status enumeration
 */
@Serializable
enum class UserStatus(val value: String) {
    @SerialName("active") ACTIVE("active"),
    @SerialName("inactive") INACTIVE("inactive"),
    @SerialName("suspended") SUSPENDED("suspended");
    
    val displayName: String
        get() = when (this) {
            ACTIVE -> "Active"
            INACTIVE -> "Inactive" 
            SUSPENDED -> "Suspended"
        }
}

/**
 * User roles with associated permissions
 */
@Serializable
enum class UserRole(val value: String) {
    @SerialName("super_admin") SUPER_ADMIN("super_admin"),
    @SerialName("admin") ADMIN("admin"),
    @SerialName("ministry_leader") MINISTRY_LEADER("ministry_leader"),
    @SerialName("volunteer") VOLUNTEER("volunteer"),
    @SerialName("member") MEMBER("member");
    
    val displayName: String
        get() = when (this) {
            SUPER_ADMIN -> "Super Admin"
            ADMIN -> "Admin"
            MINISTRY_LEADER -> "Ministry Leader"
            VOLUNTEER -> "Volunteer"
            MEMBER -> "Member"
        }
    
    val permissions: UserPermissions
        get() = when (this) {
            SUPER_ADMIN -> UserPermissions.all
            ADMIN -> UserPermissions.admin
            MINISTRY_LEADER -> UserPermissions.ministryLeader
            VOLUNTEER -> UserPermissions.volunteer
            MEMBER -> UserPermissions.member
        }
}

/**
 * Fine-grained permission system
 */
@Serializable
data class UserPermissions(
    val manageUsers: Boolean,
    val manageMembers: Boolean,
    val sendMessages: Boolean,
    val viewAnalytics: Boolean,
    val manageAutomation: Boolean,
    val manageTemplates: Boolean,
    val manageOrganization: Boolean,
    val exportData: Boolean,
    val deleteData: Boolean
) {
    companion object {
        val all = UserPermissions(
            manageUsers = true,
            manageMembers = true,
            sendMessages = true,
            viewAnalytics = true,
            manageAutomation = true,
            manageTemplates = true,
            manageOrganization = true,
            exportData = true,
            deleteData = true
        )
        
        val admin = UserPermissions(
            manageUsers = true,
            manageMembers = true,
            sendMessages = true,
            viewAnalytics = true,
            manageAutomation = true,
            manageTemplates = true,
            manageOrganization = false,
            exportData = true,
            deleteData = true
        )
        
        val ministryLeader = UserPermissions(
            manageUsers = false,
            manageMembers = true,
            sendMessages = true,
            viewAnalytics = true,
            manageAutomation = true,
            manageTemplates = true,
            manageOrganization = false,
            exportData = false,
            deleteData = false
        )
        
        val volunteer = UserPermissions(
            manageUsers = false,
            manageMembers = false,
            sendMessages = true,
            viewAnalytics = false,
            manageAutomation = false,
            manageTemplates = true,
            manageOrganization = false,
            exportData = false,
            deleteData = false
        )
        
        val member = UserPermissions(
            manageUsers = false,
            manageMembers = false,
            sendMessages = false,
            viewAnalytics = false,
            manageAutomation = false,
            manageTemplates = false,
            manageOrganization = false,
            exportData = false,
            deleteData = false
        )
    }
}

/**
 * Authentication request/response models
 */
@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val rememberMe: Boolean
)

@Serializable
data class LoginResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int
)

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("display_name") val displayName: String? = null
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String? = null,
    val expiresIn: Int? = null
)

/**
 * Authentication error handling
 */
data class AuthenticationError(
    val code: String,
    override val message: String
) : Exception(message) {
    companion object {
        val invalidCredentials = AuthenticationError(
            code = "INVALID_CREDENTIALS",
            message = "Invalid email or password"
        )
        
        val userNotFound = AuthenticationError(
            code = "USER_NOT_FOUND", 
            message = "User not found"
        )
        
        val userInactive = AuthenticationError(
            code = "USER_INACTIVE",
            message = "Account is inactive"
        )
        
        val organizationInactive = AuthenticationError(
            code = "ORGANIZATION_INACTIVE",
            message = "Organization is inactive"
        )
    }
}