package dev.esteban.vacationplanner.views.vacationlist

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.vacationplanner.navigation.ScreenNavigation

object VacationListNavigation : ScreenNavigation {

    override val route = "vacation_list"

    @Composable
    override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
        VacationListScreen()
    }
}