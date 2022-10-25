package dev.esteban.vacationplanner.views.vacationdetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.esteban.vacationplanner.commons.Map
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import dev.esteban.common.utils.ScreenState
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme
import dev.esteban.vacationplanner.R
import dev.esteban.vacationplanner.commons.CheckBoxLabel
import dev.esteban.vacationplanner.commons.SnackBarMessage
import dev.esteban.vacationplanner.viewmodel.UpdatePlaceViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun VacationDetailsScreen(
    updatePlaceViewModel: UpdatePlaceViewModel = getViewModel(),
    placeModel: PlaceModel,
    back: () -> Unit = {}
) {
    val visitedState = remember { mutableStateOf(placeModel.visited) }
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text(
                        text = placeModel.label,
                        style = VacationPlannerTheme.typography.h3,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        back()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        val uiState = updatePlaceViewModel.uiState
        if (
            uiState.screenState == ScreenState.Success ||
            uiState.screenState == ScreenState.Error
        ) {
            val message = if (uiState.placeWasUpdated) {
                R.string.place_updated_success
            } else {
                visitedState.value = !visitedState.value
                R.string.error_general
            }

            SnackBarMessage(
                show = uiState.showMessage,
                message = stringResource(id = message)
            )
        }

        VacationDetails(
            placeModel = placeModel,
            visited = visitedState.value,
        ) {
            visitedState.value = it
            updatePlaceViewModel.updatePlace(id = placeModel.id, visited = it)
        }
    }
}

@Composable
fun VacationDetails(
    placeModel: PlaceModel,
    visited: Boolean,
    updateVisited: (visited: Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Map(
            placeLocation = LatLng(placeModel.latLng[0], placeModel.latLng[1]),
            label = placeModel.label
        )
        Text(
            text = placeModel.description,
            style = VacationPlannerTheme.typography.h4
        )
        CheckBoxLabel(
            labelResource = R.string.label_have_you_visited_this_place,
            checked = visited,
            updateChecked = updateVisited
        )
    }
}
