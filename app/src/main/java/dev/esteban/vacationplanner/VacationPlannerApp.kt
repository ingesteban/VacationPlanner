package dev.esteban.vacationplanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import dev.esteban.vacationplanner.navigation.ScreenNavigation
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.esteban.vacationplanner.views.vacationlist.VacationListNavigation

@Composable
fun VacationPlannerApp() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = VacationListNavigation.route) {
            ScreenNavigation.allScreens.forEach { screenNavigation ->
                composable(screenNavigation.route) { navBackStackEntry ->
                    screenNavigation.Content(
                        navController = navController,
                        navBackStackEntry = navBackStackEntry
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VacationPlannerTheme {
        VacationPlannerApp()
    }
}