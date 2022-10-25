package dev.esteban.vacationplanner.commons

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.View.OnDragListener
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import dev.esteban.vacationplanner.R

const val TAG = "commons"
const val PROGRESS_BAR_TAG = "ProgressBarItem"
const val DISMISS_SNACK_BAR = "Snack bar dismiss"

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SnackBarMessage(
    message: String,
    show: Boolean = false,
    actionLabel: String = "",
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit = {}
) {
    if (show) {
        val snackBarHostState = remember { SnackbarHostState() }
        Box(
            modifier
                .fillMaxSize()
                .semantics { contentDescription = "error" }) {
            LaunchedEffect(Unit) {
                val result = snackBarHostState.showSnackbar(
                    message = message, actionLabel = actionLabel
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> onClickAction()
                    else -> Log.e(TAG, DISMISS_SNACK_BAR)
                }
            }
            SnackbarHost(
                hostState = snackBarHostState, modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun LoadingItem(
    color: Color = MaterialTheme.colors.primary, modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        color = color,
        modifier = modifier
            .testTag(PROGRESS_BAR_TAG)
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            )
    )
}

@Composable
fun CheckBoxLabel(
    labelResource: Int,
    checked: Boolean,
    modifier: Modifier = Modifier,
    updateChecked: (checked: Boolean) -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .clickable { updateChecked(!checked) }) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 32.dp, start = 4.dp)
                .fillMaxWidth(),
            text = stringResource(id = labelResource),
            style = VacationPlannerTheme.typography.h5
        )
        Checkbox(modifier = Modifier.align(Alignment.CenterEnd),
            checked = checked,
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
            onCheckedChange = {
                updateChecked(it)
            })
    }
}

@Composable
fun OutlinedTextCustom(
    labelResource: Int,
    text: String,
    modifier: Modifier = Modifier,
    onTextChanged: (text: String) -> Unit
) {
    val textFieldState = remember { mutableStateOf(TextFieldValue(text)) }
    OutlinedTextField(
        label = {
            Text(
                text = stringResource(id = labelResource),
                style = VacationPlannerTheme.typography.h5
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        value = textFieldState.value,
        onValueChange = {
            textFieldState.value = it
            onTextChanged(textFieldState.value.text)
        })
}

@Composable
fun Map(
    placeLocation: LatLng,
    label: String = String(),
    zoom: Float = 10f,
    draggable: Boolean = false,
    modifier: Modifier = Modifier,
    dragListener: (latLng: LatLng) -> Unit = {}
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(placeLocation, zoom)
    }
    val markerState = rememberMarkerState(position = placeLocation)

    if (markerState.dragState == DragState.END) {
        dragListener(markerState.position)
    }
    GoogleMap(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = markerState,
            title = label,
            draggable = draggable,
        )
    }
}

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    background: Color = VacationPlannerTheme.color.purple500,
    stringResource: Int,
    onClickAction: () -> Unit
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = background
        ),
        onClick = {
            onClickAction()
        }) {
        Text(
            text = stringResource(id = stringResource),
            style = VacationPlannerTheme.typography.h5,
            color = Color.White
        )
    }
}
