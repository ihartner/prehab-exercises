package com.prehab.exercises.session

import com.prehab.exercises.model.Exercise
import com.prehab.exercises.model.ExercisePhase

enum class SessionStatus { RUNNING, FINISHED }

enum class RepStage { INTRO, HOLD, REST }

data class SessionUiState(
    val exercise: Exercise? = null,
    val exerciseIndex: Int = 0,
    val totalExercises: Int = 0,
    val phase: ExercisePhase? = null,
    val stage: RepStage = RepStage.INTRO,
    val currentRep: Int = 0,
    val secondsRemaining: Int = 0,
    val isPaused: Boolean = false,
    val status: SessionStatus = SessionStatus.RUNNING
)
