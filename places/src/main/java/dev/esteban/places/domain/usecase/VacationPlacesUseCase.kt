package dev.esteban.places.domain.usecase

import dev.esteban.common.utils.UseCase
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.places.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher

class VacationPlacesUseCase(
    ioDispatcher: CoroutineDispatcher,
    private val placeRepository: PlaceRepository
) : UseCase<Unit, List<PlaceModel>>(ioDispatcher) {

    override suspend fun execute(unit: Unit): List<PlaceModel> {
        return placeRepository.getVacationPlaces()
    }
}
