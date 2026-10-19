package com.prehab.exercises.reminder

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek

private val Context.reminderDataStore by preferencesDataStore(name = "reminder_settings")

private val ENABLED = booleanPreferencesKey("enabled")
private val FREQUENCY = stringPreferencesKey("frequency")
private val DAY_OF_WEEK = intPreferencesKey("day_of_week")
private val HOUR = intPreferencesKey("hour")
private val MINUTE = intPreferencesKey("minute")

private fun Preferences.toReminderSettings(): ReminderSettings {
    val defaults = ReminderSettings()
    return ReminderSettings(
        enabled = this[ENABLED] ?: defaults.enabled,
        frequency = this[FREQUENCY]
            ?.let { runCatching { ReminderFrequency.valueOf(it) }.getOrNull() }
            ?: defaults.frequency,
        dayOfWeek = this[DAY_OF_WEEK]?.let { DayOfWeek.of(it) } ?: defaults.dayOfWeek,
        hour = this[HOUR] ?: defaults.hour,
        minute = this[MINUTE] ?: defaults.minute
    )
}

class ReminderRepository(private val context: Context) {

    val settings: Flow<ReminderSettings> =
        context.reminderDataStore.data.map { it.toReminderSettings() }

    /** Applies [transform] to the stored settings atomically and returns the saved result. */
    suspend fun update(transform: (ReminderSettings) -> ReminderSettings): ReminderSettings {
        var saved = ReminderSettings()
        context.reminderDataStore.edit { prefs ->
            saved = transform(prefs.toReminderSettings())
            prefs[ENABLED] = saved.enabled
            prefs[FREQUENCY] = saved.frequency.name
            prefs[DAY_OF_WEEK] = saved.dayOfWeek.value
            prefs[HOUR] = saved.hour
            prefs[MINUTE] = saved.minute
        }
        return saved
    }
}
