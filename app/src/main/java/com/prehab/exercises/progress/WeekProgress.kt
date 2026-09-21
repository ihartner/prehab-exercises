package com.prehab.exercises.progress

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
    // DayOfWeek.value is 1=Monday..7=Sunday (ISO-8601); this finds the most recent Sunday.
    val daysSinceSunday = today.dayOfWeek.value % 7
    val startOfWeek = today.minusDays(daysSinceSunday.toLong())
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
