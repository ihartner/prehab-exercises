package com.prehab.exercises.ui.breathing

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prehab.exercises.breathing.BreathingSessionViewModel
import com.prehab.exercises.breathing.BreathingStatus

@Composable
fun BreathingSessionScreen(
    exerciseId: Int,
    onExit: () -> Unit
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: BreathingSessionViewModel = viewModel(
        factory = BreathingSessionViewModel.Factory(application, exerciseId)
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    val exercise = state.exercise

    if (state.status == BreathingStatus.FINISHED) {
        BreathingCompleteContent(onDone = onExit)
        return
    }

    Scaffold(containerColor = MaterialTheme.colorScheme.surface) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (state.status == BreathingStatus.RUNNING) {
                    "Cycle ${state.cycle} of ${exercise.cycles}"
                } else {
                    exercise.pattern
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.cue,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            BreathingCircle(
                level = state.level,
                secondsRemaining = if (state.status == BreathingStatus.RUNNING) state.secondsRemaining else null
            )

            if (state.status == BreathingStatus.INTRO) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = exercise.intro,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(
                    onClick = { viewModel.toggleVoice() },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = if (state.voiceEnabled) {
                            Icons.AutoMirrored.Filled.VolumeUp
                        } else {
                            Icons.AutoMirrored.Filled.VolumeOff
                        },
                        contentDescription = if (state.voiceEnabled) "Mute voice" else "Unmute voice"
                    )
                }
                FilledIconButton(
                    onClick = { viewModel.togglePause() },
                    modifier = Modifier.size(72.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = if (state.isPaused) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                        contentDescription = if (state.isPaused) "Resume" else "Pause",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {
                viewModel.endSession()
                onExit()
            }) {
                Text("End Session", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun BreathingCircle(level: Float, secondsRemaining: Int?) {
    val maxSize = 280.dp
    val scale = 0.5f + 0.5f * level.coerceIn(0f, 1f)
    val primary = MaterialTheme.colorScheme.primary

    Box(modifier = Modifier.size(maxSize), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(maxSize)
                .border(2.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(maxSize * scale)
                .clip(CircleShape)
                .background(primary.copy(alpha = 0.3f))
                .border(3.dp, primary, CircleShape)
        )
        if (secondsRemaining != null) {
            Text(
                text = secondsRemaining.toString(),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BreathingCompleteContent(onDone: () -> Unit) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surface) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Well done",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Take a moment to notice how you feel.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onDone,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Done")
            }
        }
    }
}
