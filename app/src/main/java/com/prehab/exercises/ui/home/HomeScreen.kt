package com.prehab.exercises.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prehab.exercises.data.ExerciseRepository
import com.prehab.exercises.model.Exercise

@Composable
fun HomeScreen(
    onStartSession: (startIndex: Int) -> Unit
) {
    val exercises = ExerciseRepository.all

    Scaffold(
        topBar = { TopAppBar(title = { Text("Prehab Exercises") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Button(
                    onClick = { onStartSession(0) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Full Session")
                }
            }

            items(exercises, key = { it.id }) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    onClick = { onStartSession(exercises.indexOf(exercise)) }
                )
            }
        }
    }
}

@Composable
private fun ExerciseCard(exercise: Exercise, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exercise.phase.displayName, style = MaterialTheme.typography.labelMedium)
            Text(text = exercise.name, style = MaterialTheme.typography.titleMedium)
            val detail = if (exercise.reps > 0) {
                "${exercise.reps} reps, hold ${exercise.holdSeconds}s"
            } else {
                "${exercise.durationSeconds}s"
            }
            Text(text = detail, style = MaterialTheme.typography.bodySmall)
        }
    }
}
