package dev.esteban.vacationplanner.views.vacationlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import dev.esteban.vacationplanner.commons.Error

@Composable
fun VacationListScreen(
    vacationsViewModel: VacationsViewModel = getViewModel(),
    navigateToPlace: (place: PlaceModel) -> Unit = {}
) {
    LaunchedEffect(true) {
        vacationsViewModel.getVacationPlaces()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text(
                        text = stringResource(id = R.string.title_vacation_places),
                        style = VacationPlannerTheme.typography.h3,
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            val uiState = vacationsViewModel.uiState
            when {
                uiState.screenState == ScreenState.Loading -> LoadingItem()
                uiState.screenState == ScreenState.Success -> uiState.places?.let { places ->
                    PlacesList(places) { place ->
                        navigateToPlace(place)
                    }
                }
                else -> Error(
                    show = uiState.screenState == ScreenState.Error,
                    message = stringResource(id = R.string.error_general)
                )
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { /*do something*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }

        }
    }
}

@Composable
fun PlacesList(
    places: List<PlaceModel>,
    onPlaceClick: (place: PlaceModel) -> Unit = {}
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
    place: PlaceModel,
    onPlaceClick: (place: PlaceModel) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onPlaceClick(place)
            },
        elevation = 10.dp
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
