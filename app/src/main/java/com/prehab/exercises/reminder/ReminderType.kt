package com.prehab.exercises.reminder

/**
 * The independent reminders the app can schedule. Each has its own alarm, notification
 * channel (so it can be muted separately in system settings), and default schedule.
 */
enum class ReminderType(
    val requestCode: Int,
    val notificationId: Int,
    val channelId: String,
    val channelName: String,
    val title: String,
    val message: String,
    /** Where tapping the notification should land; null opens the Home screen. */
    val destination: String?,
    val defaults: ReminderSettings
) {
    EXERCISE(
        requestCode = 1001,
        notificationId = 2001,
        channelId = "exercise_reminders",
        channelName = "Exercise reminders",
        title = "Time for your prehab exercises",
        message = "Tap to start today's session.",
        destination = null,
        defaults = ReminderSettings()
    ),
    BREATHING(
        requestCode = 1002,
        notificationId = 2002,
        channelId = "breathing_reminders",
        channelName = "Breathing reminders",
        title = "Time to take a breath",
        message = "Tap to start a guided breathing exercise.",
        destination = "breathing",
        defaults = ReminderSettings(hour = 20)
    )
}
