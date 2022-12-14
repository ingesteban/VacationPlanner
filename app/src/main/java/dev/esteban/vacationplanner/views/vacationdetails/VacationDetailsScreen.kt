package dev.esteban.vacationplanner.views.vacationdetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import dev.esteban.vacationplanner.commons.Map
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.model.LatLng
import dev.esteban.common.utils.ScreenState
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme
import dev.esteban.vacationplanner.R
import dev.esteban.vacationplanner.commons.CheckBoxLabel
import dev.esteban.vacationplanner.commons.DefaultButton
import dev.esteban.vacationplanner.commons.SnackBarMessage
import dev.esteban.vacationplanner.viewmodel.VacationDetailViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun VacationDetailsScreen(
    vacationDetailViewModel: VacationDetailViewModel = getViewModel(),
    placeModel: PlaceModel,
    back: () -> Unit = {}
) {
    val visitedState = remember { mutableStateOf(placeModel.visited) }
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(topBar = {
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
                    openDialog.value = true
                }) {
                    Icon(
                        Icons.Filled.Delete, contentDescription = null, tint = Color.White
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
        )
    }) {
        val uiState = vacationDetailViewModel.uiState
        if (uiState.screenState == ScreenState.Success || uiState.screenState == ScreenState.Error) {
            if (uiState.showMessage) {
                val message = if (uiState.placeWasUpdated) {
                    R.string.place_updated_success
                } else {
                    visitedState.value = !visitedState.value
                    R.string.error_general
                }
                SnackBarMessage(show = true, message = stringResource(id = message)) {
                    vacationDetailViewModel.resetStates()
                }
            }
            if (uiState.deletePlace) {
                back()
                vacationDetailViewModel.resetStates()
            }
        }
        DeleteAlertDialog(
            openDialog = openDialog.value
        ) { executeAction ->
            openDialog.value = false
            if (executeAction) {
                vacationDetailViewModel.deletePlace(id = placeModel.id)
            }
        }
        VacationDetails(
            placeModel = placeModel,
            visited = visitedState.value,
        ) {
            visitedState.value = it
            vacationDetailViewModel.updatePlace(id = placeModel.id, visited = it)
        }
    }
}

@Composable
fun VacationDetails(
    placeModel: PlaceModel,
    visited: Boolean,
    context: Context = LocalContext.current,
    updateVisited: (visited: Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(all = 16.dp)) {

        Map(
            placeLocation = LatLng(placeModel.latLng[0], placeModel.latLng[1]),
            label = placeModel.label
        )
        DefaultButton(
            stringResource = R.string.place_see_on_google_maps
        ) {
            val gmmIntentUri = Uri.parse("google.streetview:cbll=${placeModel.latLng[0]},${placeModel.latLng[1]}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(context.packageManager)?.let {
                context.startActivity(mapIntent)
            }
        }
        Text(
            text = placeModel.description, style = VacationPlannerTheme.typography.h4
        )
        CheckBoxLabel(
            labelResource = R.string.label_have_you_visited_this_place,
            checked = visited,
            updateChecked = updateVisited
        )
    }
}

@Composable
fun DeleteAlertDialog(
    openDialog: Boolean,
    onClickAction: (executeAction: Boolean) -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                onClickAction(false)
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.title_delete_place),
                    textAlign = TextAlign.Center,
                    style = VacationPlannerTheme.typography.h1
                )
            },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.description_delete_place),
                    textAlign = TextAlign.Center,
                    style = VacationPlannerTheme.typography.h5
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DefaultButton(
                        modifier = Modifier.weight(1f),
                        background = VacationPlannerTheme.color.gray100,
                        stringResource = R.string.btn_no
                    ) {
                        onClickAction(false)
                    }
                    DefaultButton(
                        modifier = Modifier.weight(1f),
                        stringResource = R.string.btn_yes
                    ) {
                        onClickAction(true)
                    }
                }
            },
        )
    }
}
