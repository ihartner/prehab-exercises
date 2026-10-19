package com.prehab.exercises.breathing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prehab.exercises.voice.VoiceGuide
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ceil

enum class BreathingStatus { INTRO, RUNNING, FINISHED }

data class BreathingUiState(
    val exercise: BreathingExercise,
    val status: BreathingStatus = BreathingStatus.INTRO,
    val cycle: Int = 0,
    val cue: String = "Get comfortable",
    val secondsRemaining: Int = 0,
    /** 0 = lungs empty, 1 = lungs full; drives the size of the breathing circle. */
    val level: Float = 0f,
    val isPaused: Boolean = false,
    val voiceEnabled: Boolean = true
)

class BreathingSessionViewModel(
    application: Application,
    exerciseId: Int
) : AndroidViewModel(application) {

    private val exercise = BreathingRepository.byId(exerciseId)
    private val voice = VoiceGuide(application)

    private val _state = MutableStateFlow(BreathingUiState(exercise = exercise))
    val state: StateFlow<BreathingUiState> = _state.asStateFlow()

    private var sessionJob: Job? = null

    init {
        sessionJob = viewModelScope.launch { runSession() }
    }

    private suspend fun runSession() {
        sayAndWait("${exercise.name}. ${exercise.intro}")
        sayAndWait("Let's begin.")

        var level = 0f
        for (cycle in 1..exercise.cycles) {
            for (step in exercise.steps) {
                _state.update {
                    it.copy(
                        status = BreathingStatus.RUNNING,
                        cycle = cycle,
                        cue = step.cue,
                        secondsRemaining = step.seconds
                    )
                }
                say(step.cue)
                level = runStep(step, level)
            }
        }

        _state.update { it.copy(status = BreathingStatus.FINISHED, cue = "Well done") }
        sayAndWait("Well done. Take a moment to notice how you feel.")
    }

    /** Animates the breathing level across one step and returns the level it finished at. */
    private suspend fun runStep(step: BreathStep, startLevel: Float): Float {
        val totalMs = step.seconds * 1000L
        var elapsedMs = 0L
        while (elapsedMs < totalMs) {
            awaitIfPaused()
            delay(TICK_MS)
            elapsedMs += TICK_MS

            val fraction = (elapsedMs.toFloat() / totalMs).coerceAtMost(1f)
            val eased = fraction * fraction * (3f - 2f * fraction)
            val level = startLevel + (step.endLevel - startLevel) * eased
            val secondsRemaining = ceil((totalMs - elapsedMs) / 1000.0).toInt()
            _state.update { it.copy(level = level, secondsRemaining = secondsRemaining) }
        }
        return step.endLevel
    }

    private suspend fun awaitIfPaused() {
        while (_state.value.isPaused) {
            delay(200)
        }
    }

    private fun say(text: String) {
        if (_state.value.voiceEnabled) voice.speak(text)
    }

    private suspend fun sayAndWait(text: String) {
        if (_state.value.voiceEnabled) voice.speakAndWait(text) else delay(1500)
    }

    fun togglePause() {
        val paused = !_state.value.isPaused
        _state.update { it.copy(isPaused = paused) }
        if (paused) voice.stop()
    }

    fun toggleVoice() {
        val enabled = !_state.value.voiceEnabled
        _state.update { it.copy(voiceEnabled = enabled) }
        if (!enabled) voice.stop()
    }

    fun endSession() {
        sessionJob?.cancel()
        voice.stop()
    }

    override fun onCleared() {
        sessionJob?.cancel()
        voice.shutdown()
    }

    class Factory(
        private val application: Application,
        private val exerciseId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BreathingSessionViewModel(application, exerciseId) as T
        }
    }

    private companion object {
        const val TICK_MS = 50L
    }
}
