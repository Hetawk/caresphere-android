package com.ekddigital.careshpere.core.designsystem

import androidx.compose.ui.graphics.Color

/**
 * CareSphere Color Palette - Android equivalent of CareSphereColors.swift
 * Comprehensive color system supporting light/dark themes and accessibility
 */
object CareSphereColors {
    
    // MARK: - Brand Colors
    val brandPrimary = Color(0xFF8B4513)        // Warm brown - primary brand color
    val brandPrimaryMuted = Color(0xFFA0522D)   // Sienna - muted brown for subtle elements
    val brandSecondary = Color(0xFF2F4F4F)      // Dark slate gray - secondary brand
    
    // MARK: - Accent Colors  
    val accentGold = Color(0xFFDAA520)          // Goldenrod - warmth and prosperity
    val accentMaroon = Color(0xFF800000)        // Maroon - depth and sophistication
    val accentNavy = Color(0xFF191970)          // Midnight blue - trust and stability
    
    // MARK: - Background Colors
    val backgroundPrimary = Color(0xFFFAF7F2)   // Warm off-white - main background
    val backgroundSecondary = Color(0xFFF5F1EB) // Slightly darker warm background
    val backgroundCard = Color(0xFFFFFFFF)      // Pure white for cards and surfaces
    val backgroundOverlay = Color(0x80000000)   // Semi-transparent overlay
    
    // MARK: - Text Colors
    val textPrimary = Color(0xFF2C2C2E)         // Near-black for primary text
    val textSecondary = Color(0xFF48484A)       // Gray for secondary text
    val textTertiary = Color(0xFF8E8E93)        // Light gray for tertiary text
    val textPlaceholder = Color(0xFFC7C7CC)     // Placeholder text color
    val textOnPrimary = Color(0xFFFFFFFF)       // White text on primary colors
    val textLink = Color(0xFF007AFF)            // Blue for links
    
    // MARK: - Semantic Colors
    val success = Color(0xFF34C759)             // Green for success states
    val successMuted = Color(0xFFD1F2DF)        // Light green background
    val warning = Color(0xFFFF9500)             // Orange for warnings  
    val warningMuted = Color(0xFFFFF4E6)        // Light orange background
    val error = Color(0xFFFF3B30)              // Red for errors
    val errorMuted = Color(0xFFFFE6E6)          // Light red background
    val info = Color(0xFF007AFF)               // Blue for information
    val infoMuted = Color(0xFFE6F2FF)          // Light blue background
    
    // MARK: - Interactive Elements
    val buttonPrimary = brandPrimary            // Primary button background
    val buttonSecondary = accentGold            // Secondary button background
    val buttonTertiary = Color(0xFFF2F2F7)     // Tertiary button background
    val buttonDisabled = Color(0xFFE5E5EA)     // Disabled button background
    
    // MARK: - Border and Divider Colors
    val border = Color(0xFFE5E5EA)             // Standard border color
    val borderFocus = brandPrimary              // Focused input border
    val divider = Color(0xFFF2F2F7)            // Divider lines
    val separator = Color(0xFFE5E5EA)          // Content separators
    
    // MARK: - Navigation Colors
    val navigationBackground = backgroundCard    // Navigation bar background
    val navigationText = textPrimary            // Navigation text color
    val navigationIcon = textSecondary          // Navigation icon color
    val tabSelected = brandPrimary              // Selected tab color
    val tabUnselected = textTertiary           // Unselected tab color
    
    // MARK: - Input Field Colors
    val inputBackground = backgroundCard        // Input field background
    val inputBorder = border                   // Input field border
    val inputFocus = borderFocus               // Focused input border
    val inputError = error                     // Error state border
    val inputPlaceholder = textPlaceholder     // Placeholder text
    
    // MARK: - Card and Surface Colors
    val cardBackground = backgroundCard         // Card background
    val cardBorder = border                    // Card border
    val surfaceElevated = Color(0xFFFCFCFC)    // Elevated surface color
    val shadowLight = Color(0x0A000000)        // Light shadow
    val shadowMedium = Color(0x14000000)       // Medium shadow
    val shadowDark = Color(0x1F000000)         // Dark shadow
    
    // MARK: - Dark Theme Colors
    object Dark {
        val backgroundPrimary = Color(0xFF0D0C0A)      // Very dark brown
        val backgroundSecondary = Color(0xFF1C1B18)     // Dark warm background  
        val backgroundCard = Color(0xFF2C2A26)          // Dark card background
        val textPrimary = Color(0xFFFCFCFC)            // Near white text
        val textSecondary = Color(0xFFE5E5E7)          // Light gray text
        val textTertiary = Color(0xFFAEAEB2)           // Medium gray text
        val border = Color(0xFF38352F)                 // Dark border
        val divider = Color(0xFF2C2A26)                // Dark divider
        val inputBackground = Color(0xFF38352F)         // Dark input background
    }
    
    // MARK: - Accessibility Colors (High Contrast)
    object HighContrast {
        val textPrimary = Color(0xFF000000)            // Pure black text
        val backgroundPrimary = Color(0xFFFFFFFF)       // Pure white background
        val buttonPrimary = Color(0xFF000000)          // High contrast button
        val border = Color(0xFF000000)                 // High contrast border
        val focus = Color(0xFFFF0000)                  // High visibility focus
    }
}

/**
 * Material Design 3 color scheme extensions for CareSphere
 */
val LightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = CareSphereColors.brandPrimary,
    onPrimary = CareSphereColors.textOnPrimary,
    primaryContainer = CareSphereColors.brandPrimaryMuted,
    onPrimaryContainer = CareSphereColors.textPrimary,
    secondary = CareSphereColors.accentGold,
    onSecondary = Color(0xFF2C2C2E),
    secondaryContainer = CareSphereColors.accentGold.copy(alpha = 0.12f),
    onSecondaryContainer = CareSphereColors.textPrimary,
    tertiary = CareSphereColors.accentNavy,
    onTertiary = CareSphereColors.textOnPrimary,
    tertiaryContainer = CareSphereColors.accentNavy.copy(alpha = 0.12f),
    onTertiaryContainer = CareSphereColors.textPrimary,
    background = CareSphereColors.backgroundPrimary,
    onBackground = CareSphereColors.textPrimary,
    surface = CareSphereColors.backgroundCard,
    onSurface = CareSphereColors.textPrimary,
    surfaceVariant = CareSphereColors.backgroundSecondary,
    onSurfaceVariant = CareSphereColors.textSecondary,
    error = CareSphereColors.error,
    onError = CareSphereColors.textOnPrimary,
    errorContainer = CareSphereColors.errorMuted,
    onErrorContainer = CareSphereColors.error,
    outline = CareSphereColors.border,
    outlineVariant = CareSphereColors.divider
)

val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = CareSphereColors.accentGold,
    onPrimary = Color(0xFF2C2C2E),
    primaryContainer = CareSphereColors.brandPrimary,
    onPrimaryContainer = CareSphereColors.textOnPrimary,
    secondary = CareSphereColors.accentGold,
    onSecondary = Color(0xFF2C2C2E),
    secondaryContainer = CareSphereColors.accentGold.copy(alpha = 0.16f),
    onSecondaryContainer = CareSphereColors.Dark.textPrimary,
    tertiary = CareSphereColors.accentNavy,
    onTertiary = CareSphereColors.textOnPrimary,
    tertiaryContainer = CareSphereColors.accentNavy.copy(alpha = 0.16f),
    onTertiaryContainer = CareSphereColors.Dark.textPrimary,
    background = CareSphereColors.Dark.backgroundPrimary,
    onBackground = CareSphereColors.Dark.textPrimary,
    surface = CareSphereColors.Dark.backgroundCard,
    onSurface = CareSphereColors.Dark.textPrimary,
    surfaceVariant = CareSphereColors.Dark.backgroundSecondary,
    onSurfaceVariant = CareSphereColors.Dark.textSecondary,
    error = CareSphereColors.error,
    onError = CareSphereColors.textOnPrimary,
    errorContainer = CareSphereColors.error.copy(alpha = 0.12f),
    onErrorContainer = CareSphereColors.error,
    outline = CareSphereColors.Dark.border,
    outlineVariant = CareSphereColors.Dark.divider
)