package dev.esteban.places.domain.repository

import dev.esteban.places.domain.model.PlaceModel

interface PlaceRepository {
    suspend fun getVacationPlaces(): List<PlaceModel>
}