package com.prehab.exercises.model

enum class ExercisePhase(val displayName: String, val positionCue: String) {
    LYING("Lying", "Lie down on a firm, flat surface such as a bed or mat."),
    SITTING("Sitting", "Have a seat in a steady chair with your feet flat on the floor."),
    STANDING("Standing", "Stand up. Hold on to a table or counter for support.")
}

enum class ExerciseType { REPS, DURATION }

data class Exercise(
    val id: Int,
    val name: String,
    val phase: ExercisePhase,
    val type: ExerciseType,
    val instructions: List<String>,
    val reps: Int = 0,
    val holdSeconds: Int = 4,
    val restSeconds: Int = 3,
    val durationSeconds: Int = 0,
    val imageRes: Int? = null
)
