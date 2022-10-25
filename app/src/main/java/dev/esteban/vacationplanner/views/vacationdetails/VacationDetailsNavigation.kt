package dev.esteban.vacationplanner.views.vacationdetails

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.navArgument
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.vacationplanner.navigation.ScreenNavigation
import dev.esteban.vacationplanner.utils.PlaceModelParamType

object VacationDetailsNavigation : ScreenNavigation {

    const val VACATIONS_DETAILS = "vacationDetails"
    const val PLACE_MODEL = "placeModel"

    override val route = "$VACATIONS_DETAILS/{$PLACE_MODEL}"

    override val title = "vacationDetails"

    override val arguments = listOf(
        navArgument(PLACE_MODEL) { type = PlaceModelParamType() }
    )

    @Composable
    override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
        val placeModel = navBackStackEntry.arguments?.getParcelable<PlaceModel>(PLACE_MODEL)
        placeModel?.let {
            VacationDetailsScreen(placeModel = placeModel) {
                navController.popBackStack()
            }
        }
    }
}
