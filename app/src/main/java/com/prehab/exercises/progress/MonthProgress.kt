package com.prehab.exercises.progress

import java.time.LocalDate
import java.time.YearMonth

data class CalendarDay(
    val date: LocalDate,
    val isCompleted: Boolean,
    val isToday: Boolean
)

data class MonthProgress(
    val yearMonth: YearMonth,
    /** Each inner list has exactly 7 entries (Sun-Sat); null marks a padding cell outside the month. */
    val weeks: List<List<CalendarDay?>>,
    val completedCount: Int
)

fun computeMonthProgress(
    yearMonth: YearMonth,
    completedDates: Set<LocalDate>,
    today: LocalDate = LocalDate.now()
): MonthProgress {
    val firstOfMonth = yearMonth.atDay(1)
    // DayOfWeek.value is 1=Monday..7=Sunday (ISO); convert so Sunday=0..Saturday=6.
    val leadingBlanks = firstOfMonth.dayOfWeek.value % 7
    val daysInMonth = yearMonth.lengthOfMonth()

    val cells = mutableListOf<CalendarDay?>()
    repeat(leadingBlanks) { cells.add(null) }
    for (day in 1..daysInMonth) {
        val date = yearMonth.atDay(day)
        cells.add(
            CalendarDay(
                date = date,
                isCompleted = completedDates.contains(date),
                isToday = date == today
            )
        )
    }
    while (cells.size % 7 != 0) cells.add(null)

    val weeks = cells.chunked(7)
    val completedCount = weeks.flatten().count { it?.isCompleted == true }

    return MonthProgress(yearMonth = yearMonth, weeks = weeks, completedCount = completedCount)
}
