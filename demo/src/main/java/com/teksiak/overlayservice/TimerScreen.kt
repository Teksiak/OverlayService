package com.teksiak.overlayservice

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun TimerScreen(
    isOverlayPermissionGranted: Boolean,
    startTimerService: () -> Unit,
    stopTimerService: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isOverlayPermissionGranted) {
            Button(
                onClick = {
                    startTimerService()
                }
            ) {
                Text("Start Timer")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    stopTimerService()
                }
            ) {
                Text("Stop Timer")
            }
        } else {
            Text(
                text = "Display over other apps permission required",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "This app needs permission to display over other apps to show overlay content.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    grantOverlayPermission(context)
                }
            ) {
                Text("Grant Permission")
            }
        }
    }
}

private fun grantOverlayPermission(context: Context) {
    val intent = Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        "package:${context.packageName}".toUri()
    )
    context.startActivity(intent)
}