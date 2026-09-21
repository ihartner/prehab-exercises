package com.prehab.exercises.data

import com.prehab.exercises.R
import com.prehab.exercises.model.Exercise
import com.prehab.exercises.model.ExercisePhase.LYING
import com.prehab.exercises.model.ExercisePhase.SITTING
import com.prehab.exercises.model.ExercisePhase.STANDING
import com.prehab.exercises.model.ExerciseType.DURATION
import com.prehab.exercises.model.ExerciseType.REPS

/**
 * Exercise plan transcribed from PREhab exercises.pdf.
 * Phase 1 pre-surgery exercises, ordered lying -> sitting -> standing.
 */
object ExerciseRepository {

    val all: List<Exercise> = listOf(
        Exercise(
            id = 1,
            name = "Ankle Pumps",
            phase = LYING,
            type = REPS,
            instructions = listOf(
                "Lie down.",
                "Bend your ankle to move one foot up and down. Then do the other foot."
            ),
            reps = 10,
            holdSeconds = 3,
            restSeconds = 3,
            imageRes = R.drawable.ex_1
        ),
        Exercise(
            id = 2,
            name = "Simple Thigh Squeezes",
            phase = LYING,
            type = REPS,
            instructions = listOf(
                "Lie down and bring one knee up for support. Keep the kneecap and toes of your straight leg facing the ceiling.",
                "Pull your toes up toward your head.",
                "Tighten the muscles in front of your thigh and push the back of your knee into the bed."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_2
        ),
        Exercise(
            id = 3,
            name = "Harder Thigh Squeezes",
            phase = LYING,
            type = REPS,
            instructions = listOf(
                "Lie down and place a firm roll under your knee.",
                "Straighten your leg, lifting your foot off the bed."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_3
        ),
        Exercise(
            id = 4,
            name = "Thigh Lifts",
            phase = LYING,
            type = REPS,
            instructions = listOf(
                "Lie down and bring one knee up for support.",
                "Tighten your thigh muscles and lift your other leg, keeping the knee straight."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_4
        ),
        Exercise(
            id = 5,
            name = "Knee Straightening",
            phase = LYING,
            type = REPS,
            instructions = listOf(
                "Lie down and bring one knee up for support. Keep the kneecap and toes of your straight leg facing the ceiling.",
                "Place a small roll under one ankle and push the knee down toward the bed."
            ),
            reps = 5,
            holdSeconds = 30,
            restSeconds = 8,
            imageRes = R.drawable.ex_5
        ),
        Exercise(
            id = 6,
            name = "Knee Bend",
            phase = LYING,
            type = REPS,
            instructions = listOf(
                "Bend your knee by sliding your heel along the bed toward your buttocks. Keep your knee facing the ceiling.",
                "Slowly straighten your knee by sliding your heel back to the starting position."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_6
        ),
        Exercise(
            id = 7,
            name = "Core Stability",
            phase = LYING,
            type = REPS,
            instructions = listOf(
                "Lie or sit with your back supported, bend both knees, and keep your feet flat.",
                "Tighten your lower stomach muscles by pulling your belly button down towards your spine.",
                "Squeeze the pelvic muscles that stop the flow of pee. Breathe normally while holding."
            ),
            reps = 5,
            holdSeconds = 6,
            restSeconds = 5,
            imageRes = R.drawable.ex_7
        ),
        Exercise(
            id = 8,
            name = "Bridges",
            phase = LYING,
            type = DURATION,
            instructions = listOf(
                "Lying on your back with a block between your knees, inhale to prepare.",
                "Exhale, zip up through your deep core, press through your feet, and bridge your hips up.",
                "Lower and repeat for the full minute."
            ),
            durationSeconds = 60,
            imageRes = R.drawable.ex_8
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
