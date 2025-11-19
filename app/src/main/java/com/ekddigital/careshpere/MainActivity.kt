package com.ekddigital.careshpere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ekddigital.careshpere.core.designsystem.CareSphereTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity - Android equivalent of CareSphereApp.swift
 * Entry point with proper dependency injection
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            CareSphereTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ContentView()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentViewPreview() {
    CareSphereTheme {
        ContentView()
    }
}