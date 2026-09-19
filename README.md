# Prehab Exercises

Android app for tracking prehab (pre-rehabilitation) exercises.

## Stack

- Kotlin
- Jetpack Compose (Material 3)
- Gradle (Kotlin DSL, version catalog)
- minSdk 24, targetSdk / compileSdk 34

## Getting started

Open the project root in Android Studio (Koala or newer) and let it sync —
this repo's Gradle wrapper metadata is in place, but the wrapper jar itself
isn't checked in, so the first sync in Android Studio will fetch it
automatically (Android Studio does this any time the jar is missing).

## Project structure

```
app/
  src/main/java/com/prehab/exercises/
    MainActivity.kt        # app entry point, Compose UI
    model/Exercise.kt      # exercise data model
    ui/theme/               # Compose theme (color, type, theme)
  src/main/res/             # strings, launcher icon, theme
  src/test/                 # unit tests
```
