package com.prehab.exercises.progress

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

private val Context.progressDataStore by preferencesDataStore(name = "session_progress")
private val COMPLETED_DATES_KEY = stringSetPreferencesKey("completed_dates")

/**
 * Tracks which calendar days the user has done at least one exercise session.
 * Backed by DataStore so it survives process death and app restarts.
 */
class ProgressRepository(private val context: Context) {

    val completedDates: Flow<Set<LocalDate>> = context.progressDataStore.data.map { prefs ->
        prefs[COMPLETED_DATES_KEY].orEmpty().map(LocalDate::parse).toSet()
    }

    suspend fun recordCompletionToday() {
        val today = LocalDate.now().toString()
        context.progressDataStore.edit { prefs ->
            val current = prefs[COMPLETED_DATES_KEY].orEmpty()
            prefs[COMPLETED_DATES_KEY] = current + today
        }
    }
}
