package com.teksiak.overlayservice

import android.content.Intent
import androidx.compose.material3.MaterialTheme
import androidx.core.app.NotificationCompat
import com.teksiak.overlay_service.OverlayService

class TimerOverlayService: OverlayService() {

    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate() {
        super.onCreate()

        timerViewModel = TimerViewModel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_START) {
            val notification = NotificationCompat.Builder(applicationContext, "channel_id")
                .setContentTitle("Timer overlay")
                .setContentText(
                    "Timer overlay is shown"
                )
                .build()

            startForeground(1001, notification)

            timerViewModel.startTimer()

            showOverlay(
                overlayContent = {
                    MaterialTheme {
                        TimerOverlayContent(
                            elapsedTime = timerViewModel.elapsedTime,
                            isPaused = timerViewModel.isPaused,
                            onResume = { timerViewModel.startTimer() },
                            onPause = { timerViewModel.pauseTimer() },
                            onDelete = {
                                stopSelf()
                            },
                        )
                    }
                }
            )
        } else if (intent?.action == ACTION_STOP) {
            stopSelf()
        }
        return START_NOT_STICKY
    }

    companion object {
        const val ACTION_START = "com.teksiak.overlayservice.ACTION_START"
        const val ACTION_STOP = "com.teksiak.overlayservice.ACTION_STOP"
    }
}