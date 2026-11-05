package com.prehab.exercises.data

import com.prehab.exercises.R
import com.prehab.exercises.model.Exercise
import com.prehab.exercises.model.ExercisePhase.SITTING
import com.prehab.exercises.model.ExercisePhase.STANDING
import com.prehab.exercises.model.ExerciseType.REPS

/**
 * Exercise plan adapted from PREhab exercises.pdf.
 * Ordered sitting -> standing.
 */
object ExerciseRepository {

    val all: List<Exercise> = listOf(
        Exercise(
            id = 4,
            name = "Thigh Lifts",
            phase = SITTING,
            type = REPS,
            instructions = listOf(
                "Sit on a steady chair with your back supported and your feet flat on the floor.",
                "Tighten your thigh muscles and lift one leg, keeping the knee straight."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_11
        ),
        Exercise(
            id = 9,
            name = "Armchair Push-Ups",
            phase = SITTING,
            type = REPS,
            instructions = listOf(
                "Sit on a steady chair, with your feet flat on the floor.",
                "Push up with both arms to lift yourself a few inches off the seat."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_9
        ),
        Exercise(
            id = 10,
            name = "Sitting Knee Bends",
            phase = SITTING,
            type = REPS,
            instructions = listOf(
                "Sit on a steady chair with your feet flat on the floor.",
                "Slowly slide your foot back as far as you can."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_10
        ),
        Exercise(
            id = 11,
            name = "Sitting Knee Straightening",
            phase = SITTING,
            type = REPS,
            instructions = listOf(
                "Sit on a steady chair with your thigh supported.",
                "Lift your foot and straighten your knee."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_11
        ),
        Exercise(
            id = 12,
            name = "Standing Knee Bends",
            phase = STANDING,
            type = REPS,
            instructions = listOf(
                "Stand straight, tuck in your stomach, and tighten your buttocks. Hold on to a table or counter for support.",
                "Slowly bend your knee by lifting your heel towards your buttocks."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_12
        ),
        Exercise(
            id = 13,
            name = "Standing Knee Lift",
            phase = STANDING,
            type = REPS,
            instructions = listOf(
                "Hold on to a table or counter for support.",
                "Lift your knee, as if you were going up a step."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_13
        ),
        Exercise(
            id = 14,
            name = "Mini Knee Bends",
            phase = STANDING,
            type = REPS,
            instructions = listOf(
                "Hold on to a table or counter for support. Stand with your legs shoulder-width apart and your toes pointed forward.",
                "Keeping your weight on your heels, slightly bend your knees so you can still see your toes. Stick your buttocks back like you are starting to sit down."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_14
        ),
        Exercise(
            id = 15,
            name = "Heel Raises",
            phase = STANDING,
            type = REPS,
            instructions = listOf(
                "Place both hands on a supportive surface for balance. Stand with equal weight on both feet.",
                "Raise both heels off the ground, going up on your toes, then lower slowly."
            ),
            reps = 10,
            holdSeconds = 3,
            restSeconds = 3,
            imageRes = R.drawable.ex_15
        )
    )
}
