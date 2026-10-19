package com.prehab.exercises.reminder

import java.time.DayOfWeek

enum class ReminderFrequency { DAILY, WEEKLY }

data class ReminderSettings(
    val enabled: Boolean = false,
    val frequency: ReminderFrequency = ReminderFrequency.DAILY,
    val dayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    val hour: Int = 9,
    val minute: Int = 0
)
