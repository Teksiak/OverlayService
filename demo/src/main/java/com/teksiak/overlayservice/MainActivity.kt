package com.teksiak.overlayservice

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teksiak.overlayservice.ui.theme.OverlayServiceTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {

    private val _isOverlayPermissionGranted = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val isOverlayPermissionGranted by _isOverlayPermissionGranted.collectAsStateWithLifecycle()

            var hasNotificationPermission by remember {
                mutableStateOf(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        ContextCompat.checkSelfPermission(
                            context,
                            POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    } else {
                        true
                    }
                )
            }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                hasNotificationPermission = isGranted
                if (!isGranted) {
                    Toast.makeText(
                        context,
                        "Notification permission is required for foreground service.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            LaunchedEffect(hasNotificationPermission) {
                if (!hasNotificationPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }

            OverlayServiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimerScreen(
                        isOverlayPermissionGranted = isOverlayPermissionGranted,
                        startTimerService = ::startTimerService,
                        stopTimerService = ::stopTimerService,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        _isOverlayPermissionGranted.update {
            Settings.canDrawOverlays(this)
        }
    }

    private fun startTimerService() {
        Intent(this, TimerOverlayService::class.java).also {
            it.action = TimerOverlayService.ACTION_START
            startService(it)
        }
    }

    private fun stopTimerService() {
        Intent(this, TimerOverlayService::class.java).also {
            it.action = TimerOverlayService.ACTION_STOP
            startService(it)
        }
    }
}