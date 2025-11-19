# CareSphere Android App - Architecture Documentation

## Overview
This document outlines the Android app architecture created to match the iOS CareSphere app structure. The Android app uses modern Android development practices with Jetpack Compose, Hilt dependency injection, and follows clean architecture principles.

## Project Structure

### Root Package: `com.ekddigital.careshpere`

```
📁 core/
├── 📁 designsystem/
│   ├── CareSphereColors.kt      # Color system (equivalent to CareSphereColors.swift)
│   ├── CareSphereTypography.kt  # Typography system (equivalent to CareSphereTypography.swift)
│   ├── CareSphereSpacing.kt     # Spacing system (equivalent to CareSphereSpacing.swift)
│   └── CareSphereTheme.kt       # Main theme manager (equivalent to CareSphereTheme.swift)
├── 📁 models/
│   └── UserModels.kt            # User and auth models (equivalent to UserModels.swift)
└── 📁 services/
    └── (Network and domain services - to be implemented)

📁 features/
├── 📁 authentication/
│   └── AuthenticationView.kt    # Login/register UI (equivalent to AuthenticationView.swift)
├── 📁 dashboard/
│   └── MainAppView.kt          # Main app dashboard (equivalent to MainAppView.swift)
├── 📁 members/
├── 📁 messaging/
└── 📁 settings/

📁 platform/
└── (Platform-specific implementations)

📄 CareSphereApplication.kt     # Application class (equivalent to CareSphereApp.swift)
📄 MainActivity.kt              # Main activity entry point
📄 ContentView.kt               # Main coordinator (equivalent to ContentView.swift)
📄 ContentViewModel.kt          # ContentView state management
```

## Key Dependencies Added

### Build Configuration
- **Android Gradle Plugin**: 8.7.1 (fixed from non-existent 8.10.1)
- **Kotlin**: 2.0.21
- **Compose Compiler Plugin**: Required for Kotlin 2.0+

### Core Dependencies
- **Jetpack Compose BOM**: 2024.10.01
- **Material Design 3**: Latest components and theming
- **Hilt**: 2.51.1 for dependency injection
- **Navigation Compose**: 2.8.2 for navigation
- **Coroutines**: 1.8.1 for async operations
- **Kotlinx Serialization**: 1.6.3 for JSON parsing
- **Retrofit**: 2.11.0 for networking (added but not yet configured)

## Architecture Patterns

### 1. **Design System**
Mirrors the iOS design system with:
- **CareSphereColors**: Complete color palette with light/dark theme support
- **CareSphereTypography**: Typography scale matching Material Design 3
- **CareSphereSpacing**: Consistent spacing system
- **CareSphereTheme**: Theme manager with organization branding support

### 2. **State Management**
- Uses Compose state management with ViewModels
- Hilt for dependency injection
- StateFlow for reactive state updates

### 3. **Feature Structure**
Each feature follows a consistent structure:
- UI components (Composables)
- ViewModels for state management
- Repository pattern for data access (to be implemented)

### 4. **Navigation**
- Compose Navigation for screen transitions
- Type-safe navigation arguments
- Deep linking support (to be configured)

## Matching iOS Patterns

### ContentView Pattern
```kotlin
// Android equivalent of iOS ContentView
@Composable
fun ContentView(viewModel: ContentViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    if (uiState.isAuthenticated) {
        MainAppView()
    } else {
        AuthenticationView()
    }
}
```

### Theme Management
```kotlin
// Android equivalent of iOS CareSphereTheme.shared
@Composable
fun CareSphereTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    organizationBranding: OrganizationBranding? = null,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = CareSphereTypographyTheme,
        content = content
    )
}
```

### Model Structure
```kotlin
// Android equivalent of iOS User model
@Serializable
data class User(
    val id: String,
    val email: String,
    @SerialName("full_name") val fullName: String,
    // ... other properties matching iOS model
) {
    // Computed properties equivalent to iOS
    val firstName: String get() = fullName.split(" ").firstOrNull() ?: fullName
    val effectiveDisplayName: String get() = displayName ?: fullName
}
```

## Next Steps

### Immediate Implementation Needed
1. **Authentication Service**: Create AuthenticationService equivalent
2. **Network Layer**: Configure Retrofit with base URL and interceptors
3. **Data Persistence**: Add Room database or DataStore
4. **Member Models**: Implement MemberModels.kt
5. **Messaging Models**: Implement MessagingModels.kt

### Feature Development
1. **Complete Authentication Flow**: Registration, login, password reset
2. **Member Management**: CRUD operations for members
3. **Messaging System**: Template management and message sending
4. **Analytics Dashboard**: Charts and statistics
5. **Settings Management**: User preferences and organization settings

### Platform Integration
1. **Push Notifications**: Firebase Cloud Messaging
2. **Deep Linking**: App links and custom schemes
3. **Biometric Authentication**: Fingerprint/Face authentication
4. **Background Services**: For notifications and sync
5. **File Management**: Document and image handling

## Build Commands

```bash
# Build the project
./gradlew build

# Run debug build
./gradlew installDebug

# Run tests
./gradlew test

# Generate release build
./gradlew assembleRelease
```

## Compatibility

- **Minimum SDK**: 21 (Android 5.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

This architecture provides a solid foundation that matches the iOS app structure while leveraging Android's native capabilities and modern development practices.