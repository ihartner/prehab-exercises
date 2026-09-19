package com.prehab.exercises

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prehab.exercises.model.Exercise
import com.prehab.exercises.ui.theme.PrehabExercisesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrehabExercisesTheme {
                ExerciseListScreen(exercises = sampleExercises)
            }
        }
    }
}

@Composable
fun ExerciseListScreen(exercises: List<Exercise>) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Prehab Exercises") }) }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(exercises) { exercise ->
                ExerciseRow(exercise)
            }
        }
    }
}

@Composable
fun ExerciseRow(exercise: Exercise) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = exercise.name)
        Text(text = exercise.targetArea)
    }
}

val sampleExercises = listOf(
    Exercise("Band Pull-Apart", "Shoulders"),
    Exercise("Glute Bridge", "Hips / Glutes"),
    Exercise("Bird Dog", "Core / Lower Back"),
    Exercise("Wall Slide", "Shoulders"),
    Exercise("Copenhagen Plank", "Adductors")
)

@Preview(showBackground = true)
@Composable
fun ExerciseListPreview() {
    PrehabExercisesTheme {
        ExerciseListScreen(exercises = sampleExercises)
    }
}
