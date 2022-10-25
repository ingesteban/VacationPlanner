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
import dev.esteban.places.domain.usecase.VacationPlacesUseCase
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

data class VacationsUiState(
    val screenState: ScreenState = ScreenState.Empty,
    val places: List<PlaceModel>? = null,
)

class VacationsViewModel(
    val vacationPlacesUseCase: VacationPlacesUseCase
) : ViewModel() {

    private companion object {
        private const val TAG = "VacationsViewModel"
    }

    var uiState by mutableStateOf(VacationsUiState(screenState = ScreenState.Empty))
        private set

    fun getVacationPlaces() {
            viewModelScope.launch {
            uiState = uiState.copy(screenState = ScreenState.Loading)
            when (val result = vacationPlacesUseCase(Unit)) {
                is Result.Success -> handleVacationPlacesSuccess(result.data)
                is Result.Error -> handleVacationPlacesFailure(result.exception)
                else -> handleVacationPlacesFailure(exceptionUnknown())
            }
        }
    }

    private fun handleVacationPlacesSuccess(places: List<PlaceModel>) {
        uiState = uiState.copy(screenState = ScreenState.Success, places = places)
    }

    private fun handleVacationPlacesFailure(e: Exception) {
        Log.e(TAG, e.toString())
        uiState = uiState.copy(screenState = ScreenState.Error)
    }
}
