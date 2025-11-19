package com.ekddigital.careshpere.core.designsystem

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

/**
 * CareSphere Design System Theme - Android equivalent of CareSphereTheme.swift
 * Central coordinator for all theming across the application
 */

/**
 * Local composition for CareSphere spacing
 */
val LocalCareSphereSpacing = staticCompositionLocalOf { CareSphereSpacing }

/**
 * Local composition for CareSphere typography
 */
val LocalCareSphereTypography = staticCompositionLocalOf { CareSphereTypography }

/**
 * Main CareSphere theme composable
 */
@Composable
fun CareSphereTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    organizationBranding: OrganizationBranding? = null,
    accessibilitySettings: AccessibilitySettings = AccessibilitySettings(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    
    // Apply organization branding if available
    val finalColorScheme = organizationBranding?.let { branding ->
        applyOrganizationBranding(colorScheme, branding)
    } ?: colorScheme
    
    // Apply accessibility settings
    val scaledSpacing = CareSphereSpacing.AccessibilityScale(accessibilitySettings.spacingScale)
    val scaledTypography = CareSphereTypographyScale(accessibilitySettings.textSizeScale)
    
    MaterialTheme(
        colorScheme = finalColorScheme,
        typography = CareSphereTypographyTheme,
        content = {
            CompositionLocalProvider(
                LocalCareSphereSpacing provides CareSphereSpacing,
                LocalCareSphereTypography provides CareSphereTypography,
                content = content
            )
        }
    )
}

/**
 * Organization-specific branding customizations
 */
data class OrganizationBranding(
    val primaryColor: String,        // Hex color code
    val secondaryColor: String? = null,     // Optional secondary color
    val logoUrl: String? = null,           // Organization logo
    val organizationName: String   // Organization display name
) {
    
    /**
     * Convert hex to Compose Color
     */
    val primaryComposeColor: androidx.compose.ui.graphics.Color
        get() = hexToColor(primaryColor) ?: CareSphereColors.brandPrimary
    
    val secondaryComposeColor: androidx.compose.ui.graphics.Color?
        get() = secondaryColor?.let { hexToColor(it) }
    
    private fun hexToColor(hex: String): androidx.compose.ui.graphics.Color? {
        return try {
            val cleanHex = hex.removePrefix("#")
            when (cleanHex.length) {
                6 -> {
                    val colorInt = cleanHex.toLong(16)
                    androidx.compose.ui.graphics.Color(
                        red = ((colorInt shr 16) and 0xFF) / 255f,
                        green = ((colorInt shr 8) and 0xFF) / 255f,
                        blue = (colorInt and 0xFF) / 255f
                    )
                }
                8 -> {
                    val colorInt = cleanHex.toLong(16)
                    androidx.compose.ui.graphics.Color(
                        alpha = ((colorInt shr 24) and 0xFF) / 255f,
                        red = ((colorInt shr 16) and 0xFF) / 255f,
                        green = ((colorInt shr 8) and 0xFF) / 255f,
                        blue = (colorInt and 0xFF) / 255f
                    )
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Accessibility-related theme settings
 */
data class AccessibilitySettings(
    val textSizeScale: Float = 1.0f,        // 0.8 to 2.0
    val spacingScale: Float = 1.0f,         // 0.8 to 1.5  
    val highContrastMode: Boolean = false,      // For better visibility
    val reduceMotion: Boolean = false,          // Disable animations
    val boldText: Boolean = false              // Use bolder font weights
)

/**
 * Apply organization branding to color scheme
 */
private fun applyOrganizationBranding(
    colorScheme: ColorScheme,
    branding: OrganizationBranding
): ColorScheme {
    return colorScheme.copy(
        primary = branding.primaryComposeColor,
        primaryContainer = branding.primaryComposeColor.copy(alpha = 0.12f),
        secondary = branding.secondaryComposeColor ?: colorScheme.secondary
    )
}

/**
 * Theme manager for persisting theme preferences
 */
class CareSphereThemeManager(private val context: Context) {
    
    private val prefs = context.getSharedPreferences("caresphere_theme", Context.MODE_PRIVATE)
    
    fun saveColorScheme(isDark: Boolean) {
        prefs.edit().putBoolean("is_dark_theme", isDark).apply()
    }
    
    fun getColorScheme(): Boolean {
        return prefs.getBoolean("is_dark_theme", false)
    }
    
    fun saveOrganizationBranding(branding: OrganizationBranding?) {
        if (branding != null) {
            prefs.edit()
                .putString("org_primary_color", branding.primaryColor)
                .putString("org_secondary_color", branding.secondaryColor)
                .putString("org_logo_url", branding.logoUrl)
                .putString("org_name", branding.organizationName)
                .apply()
        } else {
            prefs.edit()
                .remove("org_primary_color")
                .remove("org_secondary_color") 
                .remove("org_logo_url")
                .remove("org_name")
                .apply()
        }
    }
    
    fun getOrganizationBranding(): OrganizationBranding? {
        val primaryColor = prefs.getString("org_primary_color", null)
        val orgName = prefs.getString("org_name", null)
        
        return if (primaryColor != null && orgName != null) {
            OrganizationBranding(
                primaryColor = primaryColor,
                secondaryColor = prefs.getString("org_secondary_color", null),
                logoUrl = prefs.getString("org_logo_url", null),
                organizationName = orgName
            )
        } else {
            null
        }
    }
    
    fun saveAccessibilitySettings(settings: AccessibilitySettings) {
        prefs.edit()
            .putFloat("text_size_scale", settings.textSizeScale)
            .putFloat("spacing_scale", settings.spacingScale)
            .putBoolean("high_contrast_mode", settings.highContrastMode)
            .putBoolean("reduce_motion", settings.reduceMotion)
            .putBoolean("bold_text", settings.boldText)
            .apply()
    }
    
    fun getAccessibilitySettings(): AccessibilitySettings {
        return AccessibilitySettings(
            textSizeScale = prefs.getFloat("text_size_scale", 1.0f),
            spacingScale = prefs.getFloat("spacing_scale", 1.0f),
            highContrastMode = prefs.getBoolean("high_contrast_mode", false),
            reduceMotion = prefs.getBoolean("reduce_motion", false),
            boldText = prefs.getBoolean("bold_text", false)
        )
    }
}