package com.ekddigital.careshpere.core.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CareSphere Spacing System - Android equivalent of CareSphereSpacing.swift  
 * Consistent spacing scale across the entire application
 */
object CareSphereSpacing {
    
    // MARK: - Base Spacing Units
    
    /**
     * Extra small spacing - 4dp
     * Use for: Icon padding, tight element spacing
     */
    val xs: Dp = 4.dp
    
    /**
     * Small spacing - 8dp  
     * Use for: Button padding, close element spacing
     */
    val sm: Dp = 8.dp
    
    /**
     * Medium spacing - 16dp
     * Use for: Standard padding, content spacing
     */
    val md: Dp = 16.dp
    
    /**
     * Large spacing - 24dp
     * Use for: Section spacing, card padding
     */
    val lg: Dp = 24.dp
    
    /**
     * Extra large spacing - 32dp
     * Use for: Major section spacing, screen padding
     */
    val xl: Dp = 32.dp
    
    /**
     * Extra extra large spacing - 48dp
     * Use for: Major layout divisions
     */
    val xxl: Dp = 48.dp
    
    /**
     * Triple extra large spacing - 64dp
     * Use for: Major screen sections
     */
    val xxxl: Dp = 64.dp
    
    // MARK: - Semantic Spacing Values
    
    /**
     * Standard screen horizontal padding
     */
    val screenHorizontal: Dp = md
    
    /**
     * Standard screen vertical padding
     */
    val screenVertical: Dp = lg
    
    /**
     * Standard card padding
     */
    val cardPadding: Dp = md
    
    /**
     * Standard list item padding
     */
    val listItemPadding: Dp = md
    
    /**
     * Standard button padding horizontal
     */
    val buttonHorizontal: Dp = lg
    
    /**
     * Standard button padding vertical
     */
    val buttonVertical: Dp = sm
    
    /**
     * Standard input field padding
     */
    val inputPadding: Dp = sm
    
    /**
     * Standard spacing between form elements
     */
    val formElementSpacing: Dp = md
    
    /**
     * Standard spacing between sections
     */
    val sectionSpacing: Dp = xl
    
    /**
     * Standard spacing between list items
     */
    val listItemSpacing: Dp = xs
    
    /**
     * Standard icon spacing from text
     */
    val iconSpacing: Dp = sm
    
    // MARK: - Layout Specific Spacing
    
    /**
     * Navigation bar padding
     */
    val navigationPadding: Dp = md
    
    /**
     * Tab bar padding
     */
    val tabBarPadding: Dp = sm
    
    /**
     * Toolbar padding
     */
    val toolbarPadding: Dp = md
    
    /**
     * Bottom sheet padding
     */
    val bottomSheetPadding: Dp = lg
    
    /**
     * Dialog padding
     */
    val dialogPadding: Dp = lg
    
    /**
     * Snackbar padding
     */
    val snackbarPadding: Dp = md
    
    // MARK: - Component Specific Spacing
    
    /**
     * Chip horizontal padding
     */
    val chipHorizontal: Dp = sm
    
    /**
     * Chip vertical padding
     */
    val chipVertical: Dp = xs
    
    /**
     * Badge padding
     */
    val badgePadding: Dp = xs
    
    /**
     * Avatar margin from content
     */
    val avatarMargin: Dp = sm
    
    // MARK: - Accessibility Scaling
    
    /**
     * Accessibility-aware spacing scale
     */
    class AccessibilityScale(private val scaleFactor: Float = 1.0f) {
        
        fun scale(spacing: Dp): Dp {
            return spacing * scaleFactor
        }
        
        companion object {
            const val SCALE_COMPACT = 0.8f     // Compact spacing
            const val SCALE_DEFAULT = 1.0f     // Default spacing
            const val SCALE_COMFORTABLE = 1.2f // More comfortable spacing
            const val SCALE_SPACIOUS = 1.5f    // Very spacious for accessibility
        }
    }
}

/**
 * Extension properties for commonly used spacing combinations
 */
object CareSphereSpacingCombinations {
    
    /**
     * Standard card content padding (horizontal: md, vertical: lg)
     */
    val cardContent = CardContentSpacing(
        horizontal = CareSphereSpacing.md,
        vertical = CareSphereSpacing.lg
    )
    
    /**
     * Standard button padding (horizontal: lg, vertical: sm)
     */
    val button = ButtonSpacing(
        horizontal = CareSphereSpacing.lg,
        vertical = CareSphereSpacing.sm
    )
    
    /**
     * Standard screen padding (horizontal: md, vertical: lg)
     */
    val screen = ScreenSpacing(
        horizontal = CareSphereSpacing.screenHorizontal,
        vertical = CareSphereSpacing.screenVertical
    )
    
    /**
     * Standard list item padding (horizontal: md, vertical: sm)
     */
    val listItem = ListItemSpacing(
        horizontal = CareSphereSpacing.md,
        vertical = CareSphereSpacing.sm
    )
}

// MARK: - Spacing Data Classes

data class CardContentSpacing(
    val horizontal: Dp,
    val vertical: Dp
)

data class ButtonSpacing(
    val horizontal: Dp,
    val vertical: Dp
)

data class ScreenSpacing(
    val horizontal: Dp,
    val vertical: Dp
)

data class ListItemSpacing(
    val horizontal: Dp,
    val vertical: Dp
)