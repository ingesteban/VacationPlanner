package dev.esteban.vacationplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import dev.esteban.vacationplanner.views.vacationdetails.VacationDetailsNavigation
import dev.esteban.vacationplanner.views.vacationlist.VacationListNavigation

interface ScreenNavigation {

    companion object {
        val allScreens = listOf(
            VacationListNavigation,
            VacationDetailsNavigation
        )
    }

    val title: String get() = ""

    val route: String

    val arguments: List<NamedNavArgument> get() = emptyList()

    @Composable
    fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    )
}