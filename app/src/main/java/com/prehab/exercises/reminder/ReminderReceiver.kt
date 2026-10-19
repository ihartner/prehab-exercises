package com.prehab.exercises.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Fires a reminder notification when its alarm goes off, and re-arms the next alarm.
 * Also re-arms every reminder after a reboot or app update, since both clear scheduled alarms.
 */
class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appContext = context.applicationContext
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val repository = ReminderRepository(appContext)
                if (intent.action == ReminderScheduler.ACTION_REMIND) {
                    val type = intent.getStringExtra(ReminderScheduler.EXTRA_TYPE)
                        ?.let { runCatching { ReminderType.valueOf(it) }.getOrNull() }
                        ?: ReminderType.EXERCISE
                    val settings = repository.settings(type).first()
                    if (settings.enabled) ReminderNotifier.show(appContext, type)
                    ReminderScheduler.schedule(appContext, type, settings)
                } else {
                    ReminderType.entries.forEach { type ->
                        ReminderScheduler.schedule(appContext, type, repository.settings(type).first())
                    }
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
