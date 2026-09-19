# Prehab Exercises

Android app that guides someone through a pre-surgery "prehab" exercise plan,
transcribed from `PREhab exercises.pdf`. Exercises are spoken aloud with a
built-in timer, and progress in order from **lying → sitting → standing**,
matching how the source plan is structured (exercises 1–8 lying, 9–11 sitting,
12–15 standing).

## Stack

- Kotlin, Jetpack Compose (Material 3), Navigation Compose
- `android.speech.tts.TextToSpeech` for voice guidance
- Coroutines-driven countdown timer (no extra timer libraries)
- compileSdk / targetSdk 36 (Android 16), minSdk 26

If a newer Android SDK than 36 is available in your installed Android Studio
by the time you build this, use the SDK Manager / Upgrade Assistant to bump
`compileSdk`/`targetSdk` in [app/build.gradle.kts](app/build.gradle.kts) and
the AGP/Kotlin/Compose versions in
[gradle/libs.versions.toml](gradle/libs.versions.toml) — those move faster
than this scaffold can track.

## Getting started

Open the project root in Android Studio and let it sync. The Gradle wrapper
metadata is in place, but the wrapper jar itself isn't checked in — Android
Studio fetches it automatically on first sync. Run on a Pixel device (or the
Pixel emulator profile) running the latest Android release.

Voice guidance uses the on-device TTS engine (Google Text-to-Speech is
preinstalled on Pixel devices) — no extra permissions or setup needed.

## How it works

- **Home screen** ([HomeScreen.kt](app/src/main/java/com/prehab/exercises/ui/home/HomeScreen.kt)) —
  lists every exercise grouped by phase, with a **Start Full Session** button.
- **Session screen** ([ExerciseSessionScreen.kt](app/src/main/java/com/prehab/exercises/ui/session/ExerciseSessionScreen.kt)) —
  drives one exercise at a time: TTS reads the exercise name and instructions,
  then counts down each hold/rest interval aloud and on-screen, cycling
  through all reps before moving to the next exercise. When the plan crosses
  from one phase to the next, it announces the transition (e.g. "Now let's
  move on to the sitting exercises... have a seat in a steady chair").
- Pause/Resume, Skip, and End Session controls are available throughout.

## Project structure

```
app/src/main/java/com/prehab/exercises/
  MainActivity.kt              # NavHost: home <-> session
  model/Exercise.kt            # Exercise, ExercisePhase, ExerciseType
  data/ExerciseRepository.kt   # the 15 exercises transcribed from the PDF
  voice/VoiceGuide.kt          # TextToSpeech wrapper (speak / speakAndWait)
  session/
    ExerciseSessionViewModel.kt  # timer + voice sequencing state machine
    SessionUiState.kt
  ui/home/HomeScreen.kt
  ui/session/ExerciseSessionScreen.kt
  ui/theme/                    # Compose theme (color, type, theme)
```

## Editing the exercise plan

All exercise content (name, instructions, phase, reps, hold/rest seconds)
lives in one place: [ExerciseRepository.kt](app/src/main/java/com/prehab/exercises/data/ExerciseRepository.kt).
Edit that list to change wording, timing, or add/remove exercises — the UI
and voice guidance adapt automatically.
