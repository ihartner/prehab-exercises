package com.prehab.exercises.breathing

import kotlin.math.ceil

/**
 * One phase of a breath. [endLevel] is how full the lungs (and the on-screen circle) should be
 * at the end of the step: 0 = empty, 1 = full.
 */
data class BreathStep(val cue: String, val seconds: Int, val endLevel: Float)

data class BreathingExercise(
    val id: Int,
    val name: String,
    val pattern: String,
    val tagline: String,
    val intro: String,
    val steps: List<BreathStep>,
    val cycles: Int
) {
    val totalSeconds: Int get() = steps.sumOf { it.seconds } * cycles

    val durationLabel: String
        get() {
            val minutes = ceil(totalSeconds / 60.0).toInt()
            return if (minutes <= 1) "About 1 min" else "About $minutes min"
        }
}

object BreathingRepository {

    val all: List<BreathingExercise> = listOf(
        BreathingExercise(
            id = 1,
            name = "Box Breathing",
            pattern = "4 - 4 - 4 - 4",
            tagline = "Steady and grounding. Calms your nerves and sharpens focus.",
            intro = "Box breathing. Sit comfortably with your feet on the floor and your back supported. " +
                "We'll breathe in, hold, breathe out, and hold, each for four counts. " +
                "Breathe in through your nose and out through your mouth.",
            steps = listOf(
                BreathStep("Breathe in", 4, 1f),
                BreathStep("Hold", 4, 1f),
                BreathStep("Breathe out", 4, 0f),
                BreathStep("Hold", 4, 0f)
            ),
            cycles = 6
        ),
        BreathingExercise(
            id = 2,
            name = "4-7-8 Breathing",
            pattern = "4 - 7 - 8",
            tagline = "Deeply relaxing. A slow rhythm that helps you unwind and prepare for sleep.",
            intro = "Four seven eight breathing. Rest the tip of your tongue behind your upper front teeth. " +
                "Breathe in quietly through your nose for four, hold for seven, " +
                "and breathe out slowly through your mouth for eight.",
            steps = listOf(
                BreathStep("Breathe in", 4, 1f),
                BreathStep("Hold", 7, 1f),
                BreathStep("Breathe out slowly", 8, 0f)
            ),
            cycles = 4
        ),
        BreathingExercise(
            id = 3,
            name = "Belly Breathing",
            pattern = "4 - 6",
            tagline = "Slow breaths into your belly. Eases tension and slows your heart rate.",
            intro = "Belly breathing. Place one hand on your chest and one on your belly. " +
                "Breathe in slowly through your nose and feel your belly rise. " +
                "Then breathe out slowly through pursed lips, as if blowing through a straw.",
            steps = listOf(
                BreathStep("Breathe in", 4, 1f),
                BreathStep("Breathe out slowly", 6, 0f)
            ),
            cycles = 8
        ),
        BreathingExercise(
            id = 4,
            name = "Coherent Breathing",
            pattern = "5 - 5",
            tagline = "Smooth, even breaths at about six a minute. Balances mind and body.",
            intro = "Coherent breathing. Breathe in gently through your nose for five, " +
                "and breathe out for five, with no pause in between. " +
                "Keep each breath smooth and relaxed.",
            steps = listOf(
                BreathStep("Breathe in", 5, 1f),
                BreathStep("Breathe out", 5, 0f)
            ),
            cycles = 10
        ),
        BreathingExercise(
            id = 5,
            name = "Physiological Sigh",
            pattern = "2 + 1 - 6",
            tagline = "A quick reset. Two breaths in and one long breath out melt away tension.",
            intro = "Physiological sigh. Breathe in deeply through your nose, " +
                "then take a second, short sip of air on top to fill your lungs completely. " +
                "Then let a long, slow breath out through your mouth.",
            steps = listOf(
                BreathStep("Breathe in", 2, 0.75f),
                BreathStep("A little more", 1, 1f),
                BreathStep("Breathe out slowly", 6, 0f)
            ),
            cycles = 6
        )
    )

    fun byId(id: Int): BreathingExercise = all.first { it.id == id }
}
