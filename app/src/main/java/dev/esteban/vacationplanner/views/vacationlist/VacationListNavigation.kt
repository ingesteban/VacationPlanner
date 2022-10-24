package dev.esteban.vacationplanner.views.vacationlist

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.google.gson.Gson
import dev.esteban.vacationplanner.navigation.ScreenNavigation
import dev.esteban.vacationplanner.views.vacationdetails.VacationDetailsNavigation.VACATIONS_DETAILS

object VacationListNavigation : ScreenNavigation {

    override val route = "vacationList"

    @Composable
    override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
        VacationListScreen { place ->
            val placeJson = Uri.encode(Gson().toJson(place))
            navController.navigate("$VACATIONS_DETAILS/$placeJson")
        }
    }
}
