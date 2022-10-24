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

@Composable
fun VacationsDetailScreen(
    placeModel: PlaceModel,
    back: () -> Unit = {}
) {
    val checkedState = remember { mutableStateOf(placeModel.visited) }
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
                }, actions = {
                    IconButton(onClick = {


                    }) {
                        Icon(Icons.Filled.Delete, null)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = placeModel.description,
                style = VacationPlannerTheme.typography.h4
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 30.dp),
                    text = stringResource(id = R.string.label_have_you_visited_this_place),
                    style = VacationPlannerTheme.typography.h5
                )
                Checkbox(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it }
                )
            }
        }
    }
}
