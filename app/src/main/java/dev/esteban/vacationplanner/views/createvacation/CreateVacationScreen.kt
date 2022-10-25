package dev.esteban.vacationplanner.views.createvacation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.esteban.vacationplanner.R
import dev.esteban.vacationplanner.commons.CheckBoxLabel
import dev.esteban.vacationplanner.commons.OutlinedTextCustom
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme

@Composable
fun CreateVacationScreen(
    back: () -> Unit
) {
    val savedButtonIsEnable = remember { mutableStateOf(false) }
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

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Save,
                            contentDescription = null,
                            tint = White
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        CreateVacationPlaceForm()
    }
}

@Composable
fun CreateVacationPlaceForm() {
    Column(Modifier.padding(16.dp)) {
        val labelState = remember { mutableStateOf(String()) }
        val descriptionState = remember { mutableStateOf(String()) }
        val visitedState = remember { mutableStateOf(false) }
        OutlinedTextCustom(
            labelResource = R.string.place_label,
            text = labelState.value
        ) {
            labelState.value = it
        }
        OutlinedTextCustom(
            labelResource = R.string.place_description,
            text = descriptionState.value
        ) {
            descriptionState.value = it
        }
        CheckBoxLabel(
            labelResource = R.string.place_visited,
            checked = visitedState.value,
            updateChecked = {
                visitedState.value = it
            }
        )
    }
}

@Preview
@Composable
fun CreateVacationPlaceFormPreview() {
    CreateVacationPlaceForm()
}