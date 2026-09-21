package com.prehab.exercises.ui.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prehab.exercises.progress.MonthProgress
import com.prehab.exercises.progress.ProgressRepository
import com.prehab.exercises.progress.computeMonthProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.YearMonth

class CalendarViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProgressRepository(application)
    private val _displayedMonth = MutableStateFlow(YearMonth.now())
    val displayedMonth: StateFlow<YearMonth> = _displayedMonth.asStateFlow()

    val monthProgress: StateFlow<MonthProgress> = combine(
        _displayedMonth,
        repository.completedDates
    ) { month, completedDates ->
        computeMonthProgress(month, completedDates)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = computeMonthProgress(YearMonth.now(), emptySet())
    )

    fun showPreviousMonth() {
        _displayedMonth.value = _displayedMonth.value.minusMonths(1)
    }

    fun showNextMonth() {
        _displayedMonth.value = _displayedMonth.value.plusMonths(1)
    }
}
