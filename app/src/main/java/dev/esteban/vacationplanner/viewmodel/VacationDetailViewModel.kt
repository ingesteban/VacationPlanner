package dev.esteban.vacationplanner.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.esteban.common.utils.Result
import dev.esteban.common.utils.ScreenState
import dev.esteban.common.utils.exceptionUnknown
import dev.esteban.places.domain.usecase.DeleteVacationPlaceUseCase
import dev.esteban.places.domain.usecase.DeleteVacationPlaceUseCase.Parameters as DeleteVacationParameters
import dev.esteban.places.domain.usecase.UpdatePlaceVisitedUseCase.Parameters as UpdatePlaceParameters
import dev.esteban.places.domain.usecase.UpdatePlaceVisitedUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

data class VacationDetailUiState(
    val screenState: ScreenState = ScreenState.Empty,
    val showMessage: Boolean = false,
    val placeWasUpdated: Boolean = false,
    val deletePlace: Boolean = false,
)

class VacationDetailViewModel(
    val updatePlaceVisitedUseCase: UpdatePlaceVisitedUseCase,
    val deleteVacationPlaceUseCase: DeleteVacationPlaceUseCase
) : ViewModel() {

    private companion object {
        private const val TAG = "VacationDetailViewModel"
    }

    var uiState by mutableStateOf(VacationDetailUiState())
        private set

    fun updatePlace(id: Long, visited: Boolean) {
        viewModelScope.launch {
            uiState = uiState.copy(
                screenState = ScreenState.Loading,
                showMessage = false
            )
            when (val result = updatePlaceVisitedUseCase(
                UpdatePlaceParameters(id = id, visited = visited)
            )) {
                is Result.Success -> handleUpdatePlaceSuccess()
                is Result.Error -> handleFailure(result.exception)
                else -> handleFailure(exceptionUnknown())
            }
        }
    }

    fun deletePlace(id: Long) {
        viewModelScope.launch {
            uiState = uiState.copy(
                screenState = ScreenState.Loading,
                showMessage = false,
            )
            when (val result = deleteVacationPlaceUseCase(DeleteVacationParameters(id = id))) {
                is Result.Success -> handleDeletePlaceSuccess()
                is Result.Error -> handleFailure(result.exception)
                else -> handleFailure(exceptionUnknown())
            }
        }
    }

    fun resetStates() {
        uiState = VacationDetailUiState()
    }

    private fun handleUpdatePlaceSuccess() {
        uiState = uiState.copy(
            screenState = ScreenState.Success,
            showMessage = true,
            placeWasUpdated = true
        )
    }

    private fun handleDeletePlaceSuccess() {
        uiState = uiState.copy(
            screenState = ScreenState.Success,
            deletePlace = true
        )
    }

    private fun handleFailure(e: Exception) {
        Log.e(TAG, e.toString())
        uiState = uiState.copy(
            screenState = ScreenState.Error,
            showMessage = true,
            placeWasUpdated = false
        )
    }
}
