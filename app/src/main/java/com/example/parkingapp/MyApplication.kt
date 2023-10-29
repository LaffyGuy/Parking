package com.example.parkingapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.parkingapp.util.Constance.NOTIFICATION_CHANNEL_ID1
import com.example.parkingapp.util.Constance.NOTIFICATION_CHANNEL_ID2
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID1, "Timer of parking", NotificationManager.IMPORTANCE_LOW)
            channel.setSound(null, null)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}