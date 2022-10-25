package dev.esteban.places.domain.usecase

import dev.esteban.common.utils.UseCase
import dev.esteban.places.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import dev.esteban.places.domain.usecase.DeleteVacationPlaceUseCase.Parameters

class DeleteVacationPlaceUseCase(
    ioDispatcher: CoroutineDispatcher,
    private val placeRepository: PlaceRepository
) : UseCase<Parameters, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: Parameters) {
        return placeRepository.deletePlace(id = parameters.id)
    }

    data class Parameters(
        val id: Long
    )
}
