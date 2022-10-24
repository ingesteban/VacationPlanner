package dev.esteban.places.domain.usecase

import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.places.domain.repository.PlaceRepository

class VacationPlacesUseCase(private val placeRepository: PlaceRepository) {
    suspend fun invoke(): List<PlaceModel> {
        return placeRepository.getVacationPlaces()
    }
}