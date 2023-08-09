package com.jaroidx.navigation.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.jaroidx.navigation.R

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerFCMToken()
        createNotificationChanelID()
    }

    private fun createNotificationChanelID() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = resources.getString(R.string.nav_notification_channel_id)
            val channelName = resources.getString(R.string.nav_notification_name)
            val channelDes = resources.getString(R.string.nav_notification_des)
            val channelPriority = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, channelName, channelPriority)
            channel.description = channelDes

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun registerFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", it.exception);
                return@addOnCompleteListener;
            }
            Log.d("TAG", "registerFCMToken: ${it.result}")
        }
    }
}