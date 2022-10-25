package dev.esteban.vacationplanner.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dev.esteban.common.utils.Result
import dev.esteban.common.utils.ScreenState
import dev.esteban.common.utils.exceptionUnknown
import dev.esteban.places.domain.usecase.CreateVacationPlaceUseCase
import kotlinx.coroutines.launch
import dev.esteban.places.domain.usecase.CreateVacationPlaceUseCase.Parameters as CreateVacationParameters

data class CreateVacationUiState(
    val screenState: ScreenState = ScreenState.Empty,
    val createPlace: Boolean = false,
)

class CreateVacationViewModel(
    val createVacationPlaceUseCase: CreateVacationPlaceUseCase
) : ViewModel() {

    private companion object {
        private const val TAG = "CreateVacationViewModel"
    }

    var uiState by mutableStateOf(CreateVacationUiState(screenState = ScreenState.Empty))
        private set

    fun createVacationPlace(
        label: String,
        description: String,
        visited: Boolean,
        latLng: LatLng
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(screenState = ScreenState.Loading)
            when (val result = createVacationPlaceUseCase(
                CreateVacationParameters(
                    label = label,
                    description = description,
                    visited = visited,
                    latLng = listOf(latLng.latitude, latLng.longitude),
                )
            )) {
                is Result.Success -> handleCreateVacationPlacesSuccess()
                is Result.Error -> handleFailure(result.exception)
                else -> handleFailure(exceptionUnknown())
            }
        }
    }

    fun resetStates() {
        uiState = CreateVacationUiState(screenState = ScreenState.Empty)
    }

    private fun handleCreateVacationPlacesSuccess() {
        uiState = uiState.copy(screenState = ScreenState.Success, createPlace = true)
    }

    private fun handleFailure(e: Exception) {
        Log.e(TAG, e.toString())
        uiState = uiState.copy(screenState = ScreenState.Error)
    }
}
