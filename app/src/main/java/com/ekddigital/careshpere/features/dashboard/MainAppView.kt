package com.ekddigital.careshpere.features.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ekddigital.careshpere.core.designsystem.CareSphereSpacing
import com.ekddigital.careshpere.core.designsystem.CareSphereTypography

/**
 * Main App View - Android equivalent of MainAppView.swift
 * Main dashboard after authentication
 */
@Composable
fun MainAppView() {
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(CareSphereSpacing.screenHorizontal)
    ) {
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.screenVertical))
        
        // Welcome header
        WelcomeSection()
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.xl))
        
        // Quick actions
        QuickActionsSection()
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.xl))
        
        // Recent activity or stats
        RecentActivitySection()
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.xl))
        
        // Feature navigation
        FeatureNavigationSection()
    }
}

@Composable
private fun WelcomeSection() {
    Column {
        Text(
            text = "Welcome back!",
            style = CareSphereTypography.pageTitle,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = "Here's what's happening in your community",
            style = CareSphereTypography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QuickActionsSection() {
    Column {
        Text(
            text = "Quick Actions",
            style = CareSphereTypography.sectionTitle
        )
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.md))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(CareSphereSpacing.sm)
        ) {
            items(quickActions) { action ->
                QuickActionCard(action = action)
            }
        }
    }
}

@Composable
private fun QuickActionCard(action: QuickAction) {
    Card(
        modifier = Modifier.size(120.dp),
        onClick = action.onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(CareSphereSpacing.md),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(CareSphereSpacing.sm))
            
            Text(
                text = action.title,
                style = CareSphereTypography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun RecentActivitySection() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(CareSphereSpacing.cardPadding)
        ) {
            Text(
                text = "Recent Activity",
                style = CareSphereTypography.sectionTitle
            )
            
            Spacer(modifier = Modifier.height(CareSphereSpacing.md))
            
            Text(
                text = "No recent activity to show",
                style = CareSphereTypography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun FeatureNavigationSection() {
    Column {
        Text(
            text = "Features",
            style = CareSphereTypography.sectionTitle
        )
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.md))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            features.forEach { feature ->
                FeatureCard(
                    feature = feature,
                    modifier = Modifier.weight(1f)
                )
                if (feature != features.last()) {
                    Spacer(modifier = Modifier.width(CareSphereSpacing.sm))
                }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    feature: Feature,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = feature.onClick
    ) {
        Column(
            modifier = Modifier.padding(CareSphereSpacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = feature.title,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(CareSphereSpacing.sm))
            
            Text(
                text = feature.title,
                style = CareSphereTypography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Sample data
private data class QuickAction(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

private data class Feature(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

private val quickActions = listOf(
    QuickAction("Send Message", Icons.Default.Send) { },
    QuickAction("Add Member", Icons.Default.PersonAdd) { },
    QuickAction("View Analytics", Icons.Default.Analytics) { },
    QuickAction("Settings", Icons.Default.Settings) { }
)

private val features = listOf(
    Feature("Members", Icons.Default.People) { },
    Feature("Messaging", Icons.Default.Message) { },
    Feature("Settings", Icons.Default.Settings) { }
)