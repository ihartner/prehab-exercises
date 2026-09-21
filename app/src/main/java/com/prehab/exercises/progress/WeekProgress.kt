package com.prehab.exercises.progress

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class DayStatus(
    val date: LocalDate,
    val label: String,
    val isCompleted: Boolean,
    val isToday: Boolean,
    val isFuture: Boolean
)

data class WeekProgress(
    val days: List<DayStatus>,
    val completedCount: Int,
    val goal: Int,
    val currentStreakDays: Int
)

private const val WEEKLY_GOAL = 4

fun computeWeekProgress(completedDates: Set<LocalDate>, today: LocalDate = LocalDate.now()): WeekProgress {
    val startOfWeek = today.with(DayOfWeek.MONDAY)
    val days = (0..6).map { offset ->
        val date = startOfWeek.plusDays(offset.toLong())
        DayStatus(
            date = date,
            label = date.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
            isCompleted = completedDates.contains(date),
            isToday = date == today,
            isFuture = date.isAfter(today)
        )
    }
    val completedCount = days.count { it.isCompleted }

    var streak = 0
    var cursor = today
    while (completedDates.contains(cursor)) {
        streak++
        cursor = cursor.minusDays(1)
    }

    return WeekProgress(
        days = days,
        completedCount = completedCount,
        goal = WEEKLY_GOAL,
        currentStreakDays = streak
    )
}
