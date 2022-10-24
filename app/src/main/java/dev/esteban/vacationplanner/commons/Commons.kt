package dev.esteban.vacationplanner.commons

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.esteban.common.utils.ScreenState

const val TAG = "commons"
const val PROGRESS_BAR_TAG = "ProgressBarItem"
const val DISMISS_SNACK_BAR = "Snack bar dismiss"

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SnackBarMessage(
    message: String,
    modifier: Modifier = Modifier,
    show: Boolean = false,
    actionLabel: String = "",
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
