package dev.esteban.vacationplanner.views.createvacation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import dev.esteban.common.utils.ScreenState
import dev.esteban.vacationplanner.R
import dev.esteban.vacationplanner.commons.*
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme
import dev.esteban.vacationplanner.viewmodel.CreateVacationViewModel
import dev.esteban.vacationplanner.viewmodel.VacationsViewModel
import dev.esteban.vacationplanner.views.vacationlist.PlacesList
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateVacationScreen(
    createVacationViewModel: CreateVacationViewModel = getViewModel(),
    back: () -> Unit
) {
    val savedButtonIsEnable = remember { mutableStateOf(false) }
    val labelState = remember { mutableStateOf(String()) }
    val descriptionState = remember { mutableStateOf(String()) }
    val visitedState = remember { mutableStateOf(false) }
    val latLngState = remember { mutableStateOf(LatLng(40.15000, -100.15000)) }

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text(
                        text = stringResource(id = R.string.title_create_vacation),
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
                    IconButton(
                        enabled = savedButtonIsEnable.value,
                        onClick = {
                            createVacationViewModel.createVacationPlace(
                                label = labelState.value,
                                description = descriptionState.value,
                                visited = visitedState.value,
                                latLng = latLngState.value,
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Save,
                            contentDescription = null,
                            tint = if (savedButtonIsEnable.value) {
                                White
                            } else {
                                Gray
                            }
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        val uiState = createVacationViewModel.uiState
        when {
            uiState.screenState == ScreenState.Loading -> LoadingItem()
            uiState.screenState == ScreenState.Success -> {
                back()
                createVacationViewModel.resetStates()
            }
            else -> SnackBarMessage(
                show = uiState.screenState == ScreenState.Error,
                message = stringResource(id = R.string.error_general)
            )
        }
        CreateVacationPlaceForm(
            label = labelState.value,
            description = descriptionState.value,
            visited = visitedState.value,
            latLng = latLngState.value,
            updateLabel = { label ->
                labelState.value = label
                savedButtonIsEnable.value =
                    labelState.value.isNotEmpty() && descriptionState.value.isNotEmpty()
            },
            updateDescription = { description ->
                descriptionState.value = description
                savedButtonIsEnable.value =
                    labelState.value.isNotEmpty() && descriptionState.value.isNotEmpty()
            },
            updateVisited = { visited ->
                visitedState.value = visited
            },
            updateLatLng = { latLng ->
                latLngState.value = latLng
            }
        )
    }
}

@Composable
fun CreateVacationPlaceForm(
    label: String,
    description: String,
    visited: Boolean,
    latLng: LatLng,
    updateLabel: (label: String) -> Unit,
    updateDescription: (description: String) -> Unit,
    updateVisited: (label: Boolean) -> Unit,
    updateLatLng: (latLng: LatLng) -> Unit,
) {
    Column(Modifier.padding(16.dp)) {
        OutlinedTextCustom(
            labelResource = R.string.place_label,
            text = label
        ) { label ->
            updateLabel(label)
        }
        OutlinedTextCustom(
            labelResource = R.string.place_description,
            text = description
        ) { description ->
            updateDescription(description)
        }
        CheckBoxLabel(
            labelResource = R.string.place_visited,
            checked = visited,
            updateChecked = { visited ->
                updateVisited(visited)
            }
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = stringResource(id = R.string.place_pickup_location),
            style = VacationPlannerTheme.typography.h5
        )
        Map(
            placeLocation = latLng,
            zoom = 4f,
            draggable = true
        ) {
            updateLatLng(it)
        }
    }
}
