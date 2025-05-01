package com.teksiak.overlayservice

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class DemoApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "channel_id",
            "Timer channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }
}