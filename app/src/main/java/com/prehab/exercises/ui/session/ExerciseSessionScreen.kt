package com.prehab.exercises.ui.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prehab.exercises.session.ExerciseSessionViewModel
import com.prehab.exercises.session.RepStage
import com.prehab.exercises.session.SessionStatus

@Composable
fun ExerciseSessionScreen(
    startIndex: Int,
    onSessionEnded: () -> Unit
) {
    val application = androidx.compose.ui.platform.LocalContext.current.applicationContext as android.app.Application
    val viewModel: ExerciseSessionViewModel = viewModel(
        factory = ExerciseSessionViewModel.Factory(application, startIndex)
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.status == SessionStatus.FINISHED) {
        SessionCompleteContent(onDone = onSessionEnded)
        return
    }

    val exercise = state.exercise ?: return

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LinearProgressIndicator(
                progress = { (state.exerciseIndex + 1) / state.totalExercises.toFloat() },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Exercise ${state.exerciseIndex + 1} of ${state.totalExercises} · ${exercise.phase.displayName}",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = exercise.name, style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = when (state.stage) {
                    RepStage.INTRO -> "Get ready"
                    RepStage.HOLD -> "Hold"
                    RepStage.REST -> "Rest"
                },
                style = MaterialTheme.typography.titleLarge
            )

            if (exercise.reps > 0) {
                Text(
                    text = "Rep ${state.currentRep} of ${exercise.reps}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (state.stage == RepStage.INTRO) "" else state.secondsRemaining.toString(),
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(modifier = Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = { viewModel.togglePause() }) {
                    Text(if (state.isPaused) "Resume" else "Pause")
                }
                OutlinedButton(onClick = { viewModel.skip() }) {
                    Text("Skip")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                viewModel.endSession()
                onSessionEnded()
            }) {
                Text("End Session")
            }
        }
    }
}

@Composable
private fun SessionCompleteContent(onDone: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Session complete!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onDone) {
                Text("Back to Home")
            }
        }
    }
}
