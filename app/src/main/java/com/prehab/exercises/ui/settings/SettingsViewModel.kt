package com.prehab.exercises.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prehab.exercises.reminder.ReminderNotifier
import com.prehab.exercises.reminder.ReminderRepository
import com.prehab.exercises.reminder.ReminderScheduler
import com.prehab.exercises.reminder.ReminderSettings
import com.prehab.exercises.reminder.ReminderType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ReminderRepository(application)

    /** Null until the stored settings have loaded, so the UI never flashes default values. */
    val exerciseSettings: StateFlow<ReminderSettings?> = settingsFor(ReminderType.EXERCISE)
    val breathingSettings: StateFlow<ReminderSettings?> = settingsFor(ReminderType.BREATHING)

    private fun settingsFor(type: ReminderType): StateFlow<ReminderSettings?> =
        repository.settings(type).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun update(type: ReminderType, transform: (ReminderSettings) -> ReminderSettings) {
        viewModelScope.launch {
            val saved = repository.update(type, transform)
            ReminderScheduler.schedule(getApplication(), type, saved)
        }
    }

    fun sendTestNotification(type: ReminderType) {
        ReminderNotifier.show(getApplication(), type)
    }
}
