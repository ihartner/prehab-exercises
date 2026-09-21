package com.prehab.exercises.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.ui.graphics.vector.ImageVector
import com.prehab.exercises.model.ExercisePhase

val ExercisePhase.icon: ImageVector
    get() = when (this) {
        ExercisePhase.LYING -> Icons.Filled.KingBed
        ExercisePhase.SITTING -> Icons.Filled.Chair
        ExercisePhase.STANDING -> Icons.Filled.Accessibility
    }
