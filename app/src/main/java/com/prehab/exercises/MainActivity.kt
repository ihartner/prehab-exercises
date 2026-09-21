package com.prehab.exercises

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prehab.exercises.ui.calendar.CalendarScreen
import com.prehab.exercises.ui.home.HomeScreen
import com.prehab.exercises.ui.session.ExerciseSessionScreen
import com.prehab.exercises.ui.theme.PrehabExercisesTheme

private const val ROUTE_HOME = "home"
private const val ROUTE_SESSION = "session/{startIndex}"
private const val ROUTE_CALENDAR = "calendar"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrehabExercisesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ROUTE_HOME) {
                    composable(ROUTE_HOME) {
                        HomeScreen(
                            onStartSession = { startIndex ->
                                navController.navigate("session/$startIndex")
                            },
                            onOpenCalendar = {
                                navController.navigate(ROUTE_CALENDAR)
                            }
                        )
                    }
                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(onBack = { navController.popBackStack() })
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
