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
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.places.domain.usecase.Parameters
import dev.esteban.places.domain.usecase.UpdatePlaceVisitedUseCase
import dev.esteban.places.domain.usecase.VacationPlacesUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

data class UpdatePlaceUiState(
    val showMessage: Boolean = false,
    val placeWasUpdated: Boolean = false,
)

class UpdatePlaceViewModel(
    val updatePlaceVisitedUseCase: UpdatePlaceVisitedUseCase
) : ViewModel() {

    private companion object {
        private const val TAG = "UpdatePlaceViewModel"
    }

    var uiState by mutableStateOf(UpdatePlaceUiState())
        private set

    fun updatePlace(id: Long, visited: Boolean) {
        viewModelScope.launch {
            uiState = uiState.copy(showMessage = false)
            when (val result = updatePlaceVisitedUseCase(
                Parameters(id = id, visited = visited)
            )) {
                is Result.Success -> handleUpdatePlaceSuccess()
                is Result.Error -> handleVacationPlacesFailure(result.exception)
                else -> handleVacationPlacesFailure(exceptionUnknown())
            }
        }
    }

    private fun handleUpdatePlaceSuccess() {
        uiState = uiState.copy(showMessage = true, placeWasUpdated = true)
    }

    private fun handleVacationPlacesFailure(e: Exception) {
        Log.e(TAG, e.toString())
        uiState = uiState.copy(showMessage = true, placeWasUpdated = false)
    }
}
