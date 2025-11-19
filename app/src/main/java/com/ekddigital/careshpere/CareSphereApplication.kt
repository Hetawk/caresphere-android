package com.ekddigital.careshpere

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Main application entry point with proper dependency injection
 * Android equivalent of CareSphereApp.swift
 */
@HiltAndroidApp
class CareSphereApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initialize any global services or configurations here
    }
}