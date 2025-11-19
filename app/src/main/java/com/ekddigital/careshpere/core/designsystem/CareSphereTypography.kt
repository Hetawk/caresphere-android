package com.ekddigital.careshpere.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * CareSphere Typography System - Android equivalent of CareSphereTypography.swift
 * Comprehensive typography scale supporting accessibility and readability
 */
object CareSphereTypography {
    
    // MARK: - Font Families
    private val defaultFontFamily = FontFamily.Default
    
    // MARK: - Display Styles (Largest)
    val displayLarge = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    )
    
    val displayMedium = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    )
    
    val displaySmall = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    )
    
    // MARK: - Headline Styles
    val headlineLarge = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    )
    
    val headlineMedium = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )
    
    val headlineSmall = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    )
    
    // MARK: - Title Styles
    val titleLarge = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
    
    val titleMedium = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    )
    
    val titleSmall = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    
    // MARK: - Body Styles (Most Common)
    val bodyLarge = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    
    val bodyMedium = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )
    
    val bodySmall = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )
    
    // MARK: - Label Styles
    val labelLarge = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    
    val labelMedium = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    
    val labelSmall = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    
    // MARK: - CareSphere Specific Typography
    
    /**
     * For page headings and major section titles
     */
    val pageTitle = headlineLarge.copy(
        fontWeight = FontWeight.Bold,
        color = CareSphereColors.textPrimary
    )
    
    /**
     * For section headings within pages
     */
    val sectionTitle = titleLarge.copy(
        fontWeight = FontWeight.SemiBold,
        color = CareSphereColors.textPrimary
    )
    
    /**
     * For subsection headings
     */
    val subsectionTitle = titleMedium.copy(
        fontWeight = FontWeight.Medium,
        color = CareSphereColors.textSecondary
    )
    
    /**
     * For button text
     */
    val buttonText = labelLarge.copy(
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp
    )
    
    /**
     * For navigation and tab labels
     */
    val navigation = labelMedium.copy(
        fontWeight = FontWeight.Medium,
        color = CareSphereColors.textSecondary
    )
    
    /**
     * For captions and helper text
     */
    val caption = bodySmall.copy(
        color = CareSphereColors.textTertiary
    )
    
    /**
     * For placeholder text in inputs
     */
    val placeholder = bodyMedium.copy(
        color = CareSphereColors.textPlaceholder
    )
    
    /**
     * For error messages
     */
    val errorText = bodySmall.copy(
        color = CareSphereColors.error,
        fontWeight = FontWeight.Medium
    )
    
    /**
     * For success messages
     */
    val successText = bodySmall.copy(
        color = CareSphereColors.success,
        fontWeight = FontWeight.Medium
    )
    
    /**
     * For warning messages
     */
    val warningText = bodySmall.copy(
        color = CareSphereColors.warning,
        fontWeight = FontWeight.Medium
    )
    
    /**
     * For metadata like timestamps, counts, etc.
     */
    val metadata = labelSmall.copy(
        color = CareSphereColors.textTertiary
    )
}

/**
 * Material Design 3 Typography theme for CareSphere
 */
val CareSphereTypographyTheme = Typography(
    displayLarge = CareSphereTypography.displayLarge,
    displayMedium = CareSphereTypography.displayMedium,
    displaySmall = CareSphereTypography.displaySmall,
    headlineLarge = CareSphereTypography.headlineLarge,
    headlineMedium = CareSphereTypography.headlineMedium,
    headlineSmall = CareSphereTypography.headlineSmall,
    titleLarge = CareSphereTypography.titleLarge,
    titleMedium = CareSphereTypography.titleMedium,
    titleSmall = CareSphereTypography.titleSmall,
    bodyLarge = CareSphereTypography.bodyLarge,
    bodyMedium = CareSphereTypography.bodyMedium,
    bodySmall = CareSphereTypography.bodySmall,
    labelLarge = CareSphereTypography.labelLarge,
    labelMedium = CareSphereTypography.labelMedium,
    labelSmall = CareSphereTypography.labelSmall
)

/**
 * Accessibility-aware typography scaling
 */
class CareSphereTypographyScale(private val scaleFactor: Float = 1.0f) {
    
    fun scale(style: TextStyle): TextStyle {
        return style.copy(
            fontSize = style.fontSize * scaleFactor,
            lineHeight = style.lineHeight * scaleFactor
        )
    }
    
    companion object {
        const val SCALE_SMALL = 0.85f      // Smaller text
        const val SCALE_DEFAULT = 1.0f     // Default size
        const val SCALE_LARGE = 1.15f      // Larger text for accessibility
        const val SCALE_EXTRA_LARGE = 1.3f // Extra large for accessibility
    }
}