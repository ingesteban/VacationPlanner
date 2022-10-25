package dev.esteban.vacationplanner.views.vacationdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
                        Icon(Icons.Filled.Delete, null)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        val uiState = updatePlaceViewModel.uiState
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

        VacationDetails(description = placeModel.description, visited = visitedState.value) {
            visitedState.value = it
            updatePlaceViewModel.updatePlace(id = placeModel.id, visited = it)
        }
    }
}

@Composable
fun VacationDetails(
    description: String,
    visited: Boolean,
    updateVisited: (visited: Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Text(
            text = description,
            style = VacationPlannerTheme.typography.h4
        )
        CheckBoxLabel(
            labelResource = R.string.label_have_you_visited_this_place,
            checked = visited,
            updateChecked = updateVisited
        )
    }
}
