package dev.esteban.places.domain.usecase

import dev.esteban.common.utils.UseCase
import dev.esteban.places.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import dev.esteban.places.domain.usecase.CreateVacationPlaceUseCase.Parameters

class CreateVacationPlaceUseCase(
    ioDispatcher: CoroutineDispatcher,
    private val placeRepository: PlaceRepository
) : UseCase<Parameters, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: Parameters) {
        return placeRepository.createPlace(
            label = parameters.label,
            description = parameters.description,
            visited = parameters.visited,
            latLng = parameters.latLng,
        )
    }

    data class Parameters(
        val label: String,
        val description: String,
        val visited: Boolean,
        val latLng: List<Double>
    )
}
