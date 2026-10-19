package com.prehab.exercises.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Fires the reminder notification when the alarm goes off, and re-arms the next alarm.
 * Also re-arms after a reboot or app update, since both clear scheduled alarms.
 */
class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appContext = context.applicationContext
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val settings = ReminderRepository(appContext).settings.first()
                if (intent.action == ReminderScheduler.ACTION_REMIND && settings.enabled) {
                    ReminderNotifier.show(appContext)
                }
                ReminderScheduler.schedule(appContext, settings)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
