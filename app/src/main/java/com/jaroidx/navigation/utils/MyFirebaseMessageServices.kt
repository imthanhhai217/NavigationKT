package com.jaroidx.navigation.utils

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jaroidx.navigation.R
import com.jaroidx.navigation.ui.MainActivity
import okhttp3.internal.notify

class MyFirebaseMessageServices : FirebaseMessagingService() {

    private val TAG = "MyFCM"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "onMessageReceived: $message")
            Log.d(TAG, "onMessageReceived: ${message.data.getValue("open")}")
            openTabFromNotification(message)
        }

    }

    private fun openTabFromNotification(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(Constants.OPEN_TAB_FROM_NOTIFICATION, message.data["open"])
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        showNotification(pendingIntent)
    }

    private fun showNotification(intent: PendingIntent?) {
        val channelID = resources.getString(R.string.nav_notification_channel_id)
        val builder = NotificationCompat.Builder(this,channelID)
        builder.setContentTitle("NavKT")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        intent?.let {
            builder.setContentIntent(intent)
        }
        val notification = builder.build()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(channelID.toInt(),notification)
    }
}