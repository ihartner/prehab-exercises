package com.prehab.exercises.reminder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.prehab.exercises.MainActivity
import com.prehab.exercises.R

object ReminderNotifier {

    const val EXTRA_DESTINATION = "destination"

    fun areNotificationsEnabled(context: Context): Boolean =
        context.getSystemService(NotificationManager::class.java).areNotificationsEnabled()

    fun show(context: Context, type: ReminderType) {
        val manager = context.getSystemService(NotificationManager::class.java)
        if (!manager.areNotificationsEnabled()) return

        manager.createNotificationChannel(
            NotificationChannel(type.channelId, type.channelName, NotificationManager.IMPORTANCE_DEFAULT)
        )

        val launchIntent = Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        type.destination?.let { launchIntent.putExtra(EXTRA_DESTINATION, it) }

        // A distinct request code per type keeps one notification's extras from overwriting the other's.
        val openApp = PendingIntent.getActivity(
            context,
            type.requestCode,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = Notification.Builder(context, type.channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(type.title)
            .setContentText(type.message)
            .setContentIntent(openApp)
            .setAutoCancel(true)
            .build()

        manager.notify(type.notificationId, notification)
    }
}
