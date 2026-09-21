package com.prehab.exercises.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prehab.exercises.progress.ProgressRepository
import com.prehab.exercises.progress.WeekProgress
import com.prehab.exercises.progress.computeWeekProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProgressRepository(application)

    val weekProgress: StateFlow<WeekProgress> = repository.completedDates
        .map { computeWeekProgress(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = computeWeekProgress(emptySet())
        )
}
