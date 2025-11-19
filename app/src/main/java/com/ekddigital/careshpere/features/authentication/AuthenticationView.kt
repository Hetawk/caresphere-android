package com.ekddigital.careshpere.features.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ekddigital.careshpere.R
import com.ekddigital.careshpere.core.designsystem.CareSphereSpacing
import com.ekddigital.careshpere.core.designsystem.CareSphereTypography
import com.ekddigital.careshpere.core.network.APIError
import com.ekddigital.careshpere.core.services.AuthenticationService
import kotlinx.coroutines.launch

/**
 * Authentication View - Android equivalent of AuthenticationView.swift
 * Handles login, registration, and password reset flows
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationView() {
    val context = LocalContext.current
    val authService = remember { AuthenticationService.getInstance(context) }
    val coroutineScope = rememberCoroutineScope()
    
    // Collect state from the authentication service
    val isLoading by authService.isLoading.collectAsStateWithLifecycle()
    val error by authService.error.collectAsStateWithLifecycle()
    
    // Form state
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showingSignUp by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    
    // Watch for errors
    LaunchedEffect(error) {
        showError = error != null
    }
    
    // Error dialog
    if (showError && error != null) {
        AlertDialog(
            onDismissRequest = { 
                showError = false
                authService.clearError()
            },
            title = { Text("Sign In Error") },
            text = { Text(error?.message ?: "An error occurred") },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showError = false
                        authService.clearError()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
    
    // Sign up modal sheet
    if (showingSignUp) {
        ModalBottomSheet(
            onDismissRequest = { showingSignUp = false }
        ) {
            SignUpView(
                onDismiss = { showingSignUp = false }
            )
        }
    }
    
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
            
            // Login form
            LoginForm(
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                showPassword = showPassword,
                onShowPasswordChange = { showPassword = it },
                isLoading = isLoading,
                onSignIn = {
                    coroutineScope.launch {
                        authService.login(email, password, true)
                    }
                },
                onShowSignUp = { showingSignUp = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onShowPasswordChange: (Boolean) -> Unit,
    isLoading: Boolean,
    onSignIn: () -> Unit,
    onShowSignUp: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(CareSphereSpacing.md)
    ) {
        
        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            placeholder = { Text("Enter your email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        
        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { onShowPasswordChange(!showPassword) }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (showPassword) "Hide password" else "Show password"
                    )
                }
            }
        )
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.sm))
        
        // Login button
        Button(
            onClick = onSignIn,
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(CareSphereSpacing.sm))
            }
            Text(
                text = "Sign In",
                style = CareSphereTypography.buttonText
            )
        }
        
        // Register link
        TextButton(
            onClick = onShowSignUp,
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