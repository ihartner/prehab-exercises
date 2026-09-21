package com.prehab.exercises.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green90,
    onPrimaryContainer = Green30,
    secondary = Blue40,
    onSecondary = Color.White,
    secondaryContainer = Blue90,
    onSecondaryContainer = Blue30,
    tertiary = TertiaryBlue40,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryBlue90,
    onTertiaryContainer = TertiaryBlue30,
    background = Beige99,
    onBackground = BeigeInk,
    surface = Beige99,
    onSurface = BeigeInk,
    surfaceVariant = Beige90,
    onSurfaceVariant = BeigeInkMuted,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Beige99,
    surfaceContainer = Beige95,
    surfaceContainerHigh = Beige90,
    surfaceContainerHighest = Beige80,
    outline = Beige60,
    outlineVariant = Beige80
)

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = Green30,
    primaryContainer = Green40,
    onPrimaryContainer = Green90,
    secondary = Blue80,
    onSecondary = Blue30,
    secondaryContainer = Blue40,
    onSecondaryContainer = Blue90,
    tertiary = TertiaryBlue80,
    onTertiary = TertiaryBlue30,
    tertiaryContainer = TertiaryBlue40,
    onTertiaryContainer = TertiaryBlue90,
    background = Beige10,
    onBackground = Beige95,
    surface = Beige10,
    onSurface = Beige95,
    surfaceVariant = Beige30,
    onSurfaceVariant = Beige80,
    surfaceContainerLowest = Beige10,
    surfaceContainerLow = Beige20,
    surfaceContainer = Beige20,
    surfaceContainerHigh = Beige30,
    surfaceContainerHighest = Beige30,
    outline = Beige60,
    outlineVariant = Beige30
)

@Composable
fun PrehabExercisesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
