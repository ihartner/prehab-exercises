package com.prehab.exercises

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prehab.exercises.reminder.ReminderNotifier
import com.prehab.exercises.ui.breathing.BreathingScreen
import com.prehab.exercises.ui.breathing.BreathingSessionScreen
import com.prehab.exercises.ui.calendar.CalendarScreen
import com.prehab.exercises.ui.home.HomeScreen
import com.prehab.exercises.ui.session.ExerciseSessionScreen
import com.prehab.exercises.ui.settings.SettingsScreen
import com.prehab.exercises.ui.theme.PrehabExercisesTheme

private const val ROUTE_HOME = "home"
private const val ROUTE_SESSION = "session/{startIndex}"
private const val ROUTE_CALENDAR = "calendar"
private const val ROUTE_SETTINGS = "settings"
private const val ROUTE_BREATHING = "breathing"
private const val ROUTE_BREATHING_SESSION = "breathing/{exerciseId}"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrehabExercisesTheme {
                val navController = rememberNavController()

                // A breathing reminder opens the app straight into the Breathing section.
                // The saved flag stops this re-running after a rotation or process restore.
                var launchDestinationHandled by rememberSaveable { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    if (!launchDestinationHandled &&
                        intent?.getStringExtra(ReminderNotifier.EXTRA_DESTINATION) == "breathing"
                    ) {
                        navController.navigate(ROUTE_BREATHING)
                    }
                    launchDestinationHandled = true
                }

                NavHost(navController = navController, startDestination = ROUTE_HOME) {
                    composable(ROUTE_HOME) {
                        HomeScreen(
                            onStartSession = { startIndex ->
                                navController.navigate("session/$startIndex")
                            },
                            onOpenCalendar = {
                                navController.navigate(ROUTE_CALENDAR)
                            },
                            onOpenSettings = {
                                navController.navigate(ROUTE_SETTINGS)
                            },
                            onOpenBreathing = {
                                navController.navigate(ROUTE_BREATHING)
                            }
                        )
                    }
                    composable(ROUTE_BREATHING) {
                        BreathingScreen(
                            onBack = { navController.popBackStack() },
                            onOpenExercise = { id -> navController.navigate("breathing/$id") }
                        )
                    }
                    composable(
                        route = ROUTE_BREATHING_SESSION,
                        arguments = listOf(navArgument("exerciseId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        BreathingSessionScreen(
                            exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: 1,
                            onExit = { navController.popBackStack() }
                        )
                    }
                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(onBack = { navController.popBackStack() })
                    }
                    composable(ROUTE_SETTINGS) {
                        SettingsScreen(onBack = { navController.popBackStack() })
                    }
                    composable(
                        route = ROUTE_SESSION,
                        arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0
                        ExerciseSessionScreen(
                            startIndex = startIndex,
                            onSessionEnded = {
                                navController.popBackStack(ROUTE_HOME, inclusive = false)
                            }
                        )
                    }
                }
            }
        }
    }
}
