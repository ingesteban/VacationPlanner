package dev.esteban.vacationplanner.views.vacationlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.esteban.common.utils.ScreenState
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme
import dev.esteban.vacationplanner.viewmodel.VacationsViewModel
import org.koin.androidx.compose.getViewModel
import dev.esteban.vacationplanner.R
import dev.esteban.vacationplanner.commons.LoadingItem
import dev.esteban.vacationplanner.commons.SnackBarMessage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VacationListScreen(
    vacationsViewModel: VacationsViewModel = getViewModel(),
    navigateToVacationDetails: (place: PlaceModel) -> Unit = {},
    navigateToCreateVacation: () -> Unit = {}
) {
    LaunchedEffect(true) {
        vacationsViewModel.getVacationPlaces()
    }
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text(
                    text = stringResource(id = R.string.title_vacation_places),
                    style = VacationPlannerTheme.typography.h3,
                )
            },
            actions = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        state.show()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
        )
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            val uiState = vacationsViewModel.uiState
            when {
                uiState.screenState == ScreenState.Loading -> LoadingItem()
                uiState.screenState == ScreenState.Success -> uiState.places?.let { places ->
                    PlacesList(places) { place ->
                        navigateToVacationDetails(place)
                    }
                }
                else -> SnackBarMessage(
                    show = uiState.screenState == ScreenState.Error,
                    message = stringResource(id = R.string.error_general)
                )
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                backgroundColor = MaterialTheme.colors.primary,
                onClick = { navigateToCreateVacation() },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.White
                )
            }
            ModalBottomSheetFilter(
                state
            )
        }
    }
}

@Composable
fun PlacesList(
    places: List<PlaceModel>, onPlaceClick: (place: PlaceModel) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        places.forEach { place ->
            item {
                PlaceItem(place) { place ->
                    onPlaceClick(place)
                }
            }
        }
    }
}

@Composable
fun PlaceItem(
    place: PlaceModel, onPlaceClick: (place: PlaceModel) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onPlaceClick(place)
            }, elevation = 10.dp
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 24.dp)
            ) {
                Text(
                    text = place.label,
                    style = VacationPlannerTheme.typography.h1,
                )
                Text(
                    text = place.description,
                    style = VacationPlannerTheme.typography.h2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = Color.Gray,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ModalBottomSheetFilter(
    state: ModalBottomSheetState
) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            val radioOptions = listOf("All", "Visited", "Unvisited")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            Column {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary)
                        )
                        Text(
                            text = text,
                            style = VacationPlannerTheme.typography.h1,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    ) { }
}
