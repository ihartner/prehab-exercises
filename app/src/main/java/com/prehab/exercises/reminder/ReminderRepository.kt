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

private class ReminderKeys(
    val enabled: Preferences.Key<Boolean>,
    val frequency: Preferences.Key<String>,
    val dayOfWeek: Preferences.Key<Int>,
    val hour: Preferences.Key<Int>,
    val minute: Preferences.Key<Int>
)

// The exercise reminder keeps its original key names so existing saved settings still load.
private val EXERCISE_KEYS = ReminderKeys(
    enabled = booleanPreferencesKey("enabled"),
    frequency = stringPreferencesKey("frequency"),
    dayOfWeek = intPreferencesKey("day_of_week"),
    hour = intPreferencesKey("hour"),
    minute = intPreferencesKey("minute")
)

private val BREATHING_KEYS = ReminderKeys(
    enabled = booleanPreferencesKey("breathing_enabled"),
    frequency = stringPreferencesKey("breathing_frequency"),
    dayOfWeek = intPreferencesKey("breathing_day_of_week"),
    hour = intPreferencesKey("breathing_hour"),
    minute = intPreferencesKey("breathing_minute")
)

private fun ReminderType.keys(): ReminderKeys = when (this) {
    ReminderType.EXERCISE -> EXERCISE_KEYS
    ReminderType.BREATHING -> BREATHING_KEYS
}

private fun Preferences.toReminderSettings(type: ReminderType): ReminderSettings {
    val keys = type.keys()
    val defaults = type.defaults
    return ReminderSettings(
        enabled = this[keys.enabled] ?: defaults.enabled,
        frequency = this[keys.frequency]
            ?.let { runCatching { ReminderFrequency.valueOf(it) }.getOrNull() }
            ?: defaults.frequency,
        dayOfWeek = this[keys.dayOfWeek]?.let { DayOfWeek.of(it) } ?: defaults.dayOfWeek,
        hour = this[keys.hour] ?: defaults.hour,
        minute = this[keys.minute] ?: defaults.minute
    )
}

class ReminderRepository(private val context: Context) {

    fun settings(type: ReminderType): Flow<ReminderSettings> =
        context.reminderDataStore.data.map { it.toReminderSettings(type) }

    /** Applies [transform] to the stored settings atomically and returns the saved result. */
    suspend fun update(
        type: ReminderType,
        transform: (ReminderSettings) -> ReminderSettings
    ): ReminderSettings {
        var saved = type.defaults
        context.reminderDataStore.edit { prefs ->
            saved = transform(prefs.toReminderSettings(type))
            val keys = type.keys()
            prefs[keys.enabled] = saved.enabled
            prefs[keys.frequency] = saved.frequency.name
            prefs[keys.dayOfWeek] = saved.dayOfWeek.value
            prefs[keys.hour] = saved.hour
            prefs[keys.minute] = saved.minute
        }
        return saved
    }
}
