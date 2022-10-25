package dev.esteban.vacationplanner.commons

import android.annotation.SuppressLint
import android.util.Log
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
                .semantics { contentDescription = "error" }
        ) {
            LaunchedEffect(Unit) {
                val result = snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> onClickAction()
                    else -> Log.e(TAG, DISMISS_SNACK_BAR)
                }
            }
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun LoadingItem(
    color: Color = MaterialTheme.colors.primary,
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        color = color,
        modifier =
        modifier
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable { updateChecked(!checked) }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 32.dp, start = 4.dp)
                .fillMaxWidth(),
            text = stringResource(id = labelResource),
            style = VacationPlannerTheme.typography.h5
        )
        Checkbox(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            checked = checked,
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
            onCheckedChange = {
                updateChecked(it)
            }
        )
    }
}


@Composable
fun OutlinedTextCustom(
    labelResource: Int,
    text: String,
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        value = textFieldState.value,
        onValueChange = {
            textFieldState.value = it
            onTextChanged(textFieldState.value.text)
        }
    )
}
