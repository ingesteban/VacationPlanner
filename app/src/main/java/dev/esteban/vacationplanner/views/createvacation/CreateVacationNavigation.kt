package dev.esteban.vacationplanner.views.createvacation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.vacationplanner.navigation.ScreenNavigation

object CreateVacationNavigation : ScreenNavigation {

    override val route = "createVacation"

    @Composable
    override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
        CreateVacationScreen() {
            navController.popBackStack()
        }
    }
}
