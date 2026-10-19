package com.prehab.exercises.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

object ReminderScheduler {

    const val ACTION_REMIND = "com.prehab.exercises.ACTION_REMIND"
    private const val REQUEST_CODE = 1001

    /** The next moment the reminder should fire, strictly after [now]. */
    fun nextTrigger(settings: ReminderSettings, now: ZonedDateTime = ZonedDateTime.now()): ZonedDateTime {
        val time = LocalTime.of(settings.hour, settings.minute)
        return when (settings.frequency) {
            ReminderFrequency.DAILY -> {
                val today = now.with(time)
                if (today.isAfter(now)) today else today.plusDays(1)
            }
            ReminderFrequency.WEEKLY -> {
                val thisWeek = now.with(TemporalAdjusters.nextOrSame(settings.dayOfWeek)).with(time)
                if (thisWeek.isAfter(now)) thisWeek else thisWeek.plusWeeks(1)
            }
        }
    }

    /**
     * Arms a single alarm for the next occurrence (or cancels it when disabled).
     * The receiver re-arms after each firing, so one-shot alarms act as a repeating schedule.
     * Uses an inexact alarm so no exact-alarm permission is needed; delivery can drift a few
     * minutes when the device is dozing.
     */
    fun schedule(context: Context, settings: ReminderSettings) {
        if (!settings.enabled) {
            cancel(context)
            return
        }
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val triggerAtMillis = nextTrigger(settings).toInstant().toEpochMilli()
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent(context))
    }

    fun cancel(context: Context) {
        context.getSystemService(AlarmManager::class.java).cancel(pendingIntent(context))
    }

    private fun pendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).setAction(ACTION_REMIND)
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
