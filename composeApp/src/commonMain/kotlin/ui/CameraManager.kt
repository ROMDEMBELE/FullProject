package ui

import androidx.compose.runtime.Composable

// CameraManager.kt
@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager

expect class CameraManager(onLaunch: () -> Unit) {
    fun launch()
}