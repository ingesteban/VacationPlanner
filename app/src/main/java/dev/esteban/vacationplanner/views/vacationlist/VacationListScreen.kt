package dev.esteban.vacationplanner.views.vacationlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme

@Composable
fun VacationListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text("Vacation Activities")
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        LazyColumn {
            item {
                VacationItem(
                    title = "Cloud Gate",
                    description = "Cloud Gate is a public sculpture by Indian-born British artist Sir Anish Kapoor"
                )
            }
            item {
                VacationItem(
                    title = "Mount Wilson Observatory",
                    description = "The Mount Wilson Observatory (MWO) is an astronomical observatory in Los Angeles County, California"
                )
            }
            item {
                VacationItem(
                    title = "Jamestown",
                    description = "Capital of the British Overseas Territory of Saint Helena, Ascension and Tristan da Cunha."
                )
            }
            item {
                VacationItem(
                    title = "Mount Kenya",
                    description = "Mount Kenya is the highest mountain in Kenya and the second-highest in Africa, after Kilimanjaro."
                )
            }
        }
    }
}

@Composable
fun VacationItem(
    title: String,
    description: String,
    onVacationClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onVacationClick),
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
                    text = title,
                    style = VacationPlannerTheme.typography.h1,
                )
                Text(
                    text = description,
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
