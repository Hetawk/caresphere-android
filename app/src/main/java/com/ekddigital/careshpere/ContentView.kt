package com.ekddigital.careshpere

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekddigital.careshpere.core.designsystem.CareSphereTypography
import com.ekddigital.careshpere.features.authentication.AuthenticationView
import com.ekddigital.careshpere.features.dashboard.MainAppView

/**
 * Main app coordinator handling authentication state and app flow
 * Android equivalent of ContentView.swift
 */
@Composable
fun ContentView(
    viewModel: ContentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Show loading state during initial load
    if (uiState.isLoading) {
        LoadingView()
        return
    }
    
    // Show authentication or main app based on auth state
    if (uiState.isAuthenticated) {
        MainAppView()
    } else {
        AuthenticationView()
    }
}

/**
 * Loading screen shown during app initialization
 */
@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Loading CareSphere...",
                style = CareSphereTypography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}