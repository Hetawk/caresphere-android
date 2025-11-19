package com.ekddigital.careshpere.features.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ekddigital.careshpere.R
import com.ekddigital.careshpere.core.designsystem.CareSphereSpacing
import com.ekddigital.careshpere.core.designsystem.CareSphereTypography

/**
 * Authentication View - Android equivalent of AuthenticationView.swift
 * Handles login, registration, and password reset flows
 */
@Composable
fun AuthenticationView() {
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(CareSphereSpacing.screenHorizontal),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            
            // App logo/branding
            Image(
                painter = painterResource(id = R.drawable.caresphere_logo),
                contentDescription = "CareSphere Logo",
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(CareSphereSpacing.xl))
            
            Text(
                text = "Welcome to CareSphere",
                style = CareSphereTypography.sectionTitle,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(CareSphereSpacing.sm))
            
            Text(
                text = "Connect, Care, and Communicate with your community",
                style = CareSphereTypography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(CareSphereSpacing.xl))
            
            // Login form will go here
            LoginForm()
        }
    }
}

@Composable
private fun LoginForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(CareSphereSpacing.md)
    ) {
        
        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        // Remember me checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it }
            )
            Text(
                text = "Remember me",
                style = CareSphereTypography.bodyMedium,
                modifier = Modifier.padding(start = CareSphereSpacing.sm)
            )
        }
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.sm))
        
        // Login button
        Button(
            onClick = { 
                // TODO: Handle login
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text(
                text = "Sign In",
                style = CareSphereTypography.buttonText
            )
        }
        
        // Register link
        TextButton(
            onClick = { 
                // TODO: Navigate to registration
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account? Register here")
        }
        
        // Forgot password link
        TextButton(
            onClick = { 
                // TODO: Handle forgot password
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Forgot Password?")
        }
    }
}