package com.prehab.exercises.session

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prehab.exercises.data.ExerciseRepository
import com.prehab.exercises.model.Exercise
import com.prehab.exercises.model.ExerciseType
import com.prehab.exercises.progress.ProgressRepository
import com.prehab.exercises.voice.VoiceGuide
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseSessionViewModel(
    application: Application,
    private val startIndex: Int
) : AndroidViewModel(application) {

    private val exercises: List<Exercise> = ExerciseRepository.all
    private val voice = VoiceGuide(application)
    private val progressRepository = ProgressRepository(application)

    private val _state = MutableStateFlow(SessionUiState(totalExercises = exercises.size))
    val state: StateFlow<SessionUiState> = _state.asStateFlow()

    private var sessionJob: Job? = null

    init {
        runFrom(startIndex)
    }

    private fun runFrom(index: Int) {
        sessionJob?.cancel()
        sessionJob = viewModelScope.launch {
            var currentIndex = index
            var previousPhase = exercises.getOrNull(index - 1)?.phase
            while (currentIndex < exercises.size) {
                val exercise = exercises[currentIndex]
                if (previousPhase != exercise.phase) {
                    announcePhaseTransition(exercise)
                }
                previousPhase = exercise.phase
                runExercise(currentIndex, exercise)
                currentIndex++
            }
            _state.update { it.copy(status = SessionStatus.FINISHED) }
            progressRepository.recordCompletionToday()
            voice.speakAndWait("Great job. You've completed today's session.")
        }
    }

    private suspend fun announcePhaseTransition(exercise: Exercise) {
        voice.speakAndWait(
            "Now let's move on to the ${exercise.phase.displayName.lowercase()} exercises. " +
                exercise.phase.positionCue
        )
    }

    private suspend fun runExercise(index: Int, exercise: Exercise) {
        _state.update {
            it.copy(
                exercise = exercise,
                exerciseIndex = index,
                phase = exercise.phase,
                stage = RepStage.INTRO,
                currentRep = 0,
                secondsRemaining = 0
            )
        }
        voice.speakAndWait("${exercise.name}. ${exercise.instructions.joinToString(" ")}")

        when (exercise.type) {
            ExerciseType.REPS -> runRepsExercise(exercise)
            ExerciseType.DURATION -> runDurationExercise(exercise)
        }
    }

    private suspend fun runRepsExercise(exercise: Exercise) {
        for (rep in 1..exercise.reps) {
            awaitIfPaused()
            _state.update { it.copy(stage = RepStage.HOLD, currentRep = rep) }
            voice.speak("Rep $rep")
            countdown(exercise.holdSeconds)

            if (rep < exercise.reps) {
                _state.update { it.copy(stage = RepStage.REST) }
                voice.speak("Rest")
                countdown(exercise.restSeconds)
            }
        }
    }

    private suspend fun runDurationExercise(exercise: Exercise) {
        _state.update { it.copy(stage = RepStage.HOLD, currentRep = 1) }
        countdown(exercise.durationSeconds)
    }

    private suspend fun countdown(seconds: Int) {
        var remaining = seconds
        _state.update { it.copy(secondsRemaining = remaining) }
        while (remaining > 0) {
            awaitIfPaused()
            delay(1000)
            remaining--
            _state.update { it.copy(secondsRemaining = remaining) }
        }
    }

    private suspend fun awaitIfPaused() {
        while (_state.value.isPaused) {
            delay(200)
        }
    }

    fun togglePause() {
        val paused = !_state.value.isPaused
        _state.update { it.copy(isPaused = paused) }
        if (paused) voice.stop()
    }

    fun skip() {
        val nextIndex = _state.value.exerciseIndex + 1
        voice.stop()
        _state.update { it.copy(isPaused = false) }
        runFrom(nextIndex)
    }

    fun endSession() {
        sessionJob?.cancel()
        voice.stop()
        _state.update { it.copy(status = SessionStatus.FINISHED) }
        viewModelScope.launch { progressRepository.recordCompletionToday() }
    }

    override fun onCleared() {
        sessionJob?.cancel()
        voice.shutdown()
    }

    class Factory(
        private val application: Application,
        private val startIndex: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExerciseSessionViewModel(application, startIndex) as T
        }
    }
}
