package com.ekddigital.careshpere.features.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ekddigital.careshpere.core.designsystem.CareSphereSpacing
import com.ekddigital.careshpere.core.designsystem.CareSphereTypography
import com.ekddigital.careshpere.core.network.APIError
import com.ekddigital.careshpere.core.services.AuthenticationService
import kotlinx.coroutines.launch

/**
 * Sign up view for new user registration
 * Android equivalent of SignUpView.swift
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val authService = remember { AuthenticationService.getInstance(context) }
    val coroutineScope = rememberCoroutineScope()
    
    // Collect state from the authentication service
    val isLoading by authService.isLoading.collectAsStateWithLifecycle()
    val error by authService.error.collectAsStateWithLifecycle()
    
    // Form state
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    
    // Form validation
    val isFormValid = fullName.isNotBlank() && 
                     email.isNotBlank() && 
                     password.isNotBlank() && 
                     password == confirmPassword && 
                     password.length >= 8
    
    // Watch for authentication success
    LaunchedEffect(authService.currentUser.collectAsStateWithLifecycle().value) {
        if (authService.currentUser.value != null) {
            onDismiss()
        }
    }
    
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
            title = { Text("Registration Error") },
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
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(CareSphereSpacing.lg)
    ) {
        // Header with close button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Create Account",
                style = CareSphereTypography.displaySmall,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.sm))
        
        Text(
            text = "Join our community",
            style = CareSphereTypography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Start
        )
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.xl))
        
        // Sign up form
        SignUpForm(
            fullName = fullName,
            onFullNameChange = { fullName = it },
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = { confirmPassword = it },
            showPassword = showPassword,
            onShowPasswordChange = { showPassword = it },
            showConfirmPassword = showConfirmPassword,
            onShowConfirmPasswordChange = { showConfirmPassword = it },
            isLoading = isLoading,
            isFormValid = isFormValid,
            onSignUp = {
                coroutineScope.launch {
                    authService.register(
                        email = email,
                        password = password,
                        fullName = fullName
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpForm(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onShowPasswordChange: (Boolean) -> Unit,
    showConfirmPassword: Boolean,
    onShowConfirmPasswordChange: (Boolean) -> Unit,
    isLoading: Boolean,
    isFormValid: Boolean,
    onSignUp: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(CareSphereSpacing.lg)
    ) {
        // Full name field
        OutlinedTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            label = { Text("Full Name") },
            placeholder = { Text("Enter your full name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        
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
        
        // Confirm password field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirm Password") },
            placeholder = { Text("Confirm your password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { onShowConfirmPasswordChange(!showConfirmPassword) }) {
                    Icon(
                        imageVector = if (showConfirmPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (showConfirmPassword) "Hide password" else "Show password"
                    )
                }
            }
        )
        
        // Password requirements (show when password is entered)
        if (password.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(CareSphereSpacing.md),
                    verticalArrangement = Arrangement.spacedBy(CareSphereSpacing.xs)
                ) {
                    PasswordRequirement(
                        text = "At least 8 characters",
                        isMet = password.length >= 8
                    )
                    
                    if (confirmPassword.isNotEmpty()) {
                        PasswordRequirement(
                            text = "Passwords match",
                            isMet = password == confirmPassword
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(CareSphereSpacing.sm))
        
        // Create account button
        Button(
            onClick = onSignUp,
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(CareSphereSpacing.sm))
            }
            Text(
                text = "Create Account",
                style = CareSphereTypography.buttonText
            )
        }
    }
}

@Composable
private fun PasswordRequirement(
    text: String,
    isMet: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CareSphereSpacing.sm)
    ) {
        Icon(
            imageVector = if (isMet) Icons.Default.Close else Icons.Default.Close, // Replace with checkmark/x icons
            contentDescription = null,
            tint = if (isMet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = CareSphereTypography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}