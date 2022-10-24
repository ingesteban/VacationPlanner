package dev.esteban.places.data.repository

import dev.esteban.places.data.datasource.local.PlaceLocalDataSource
import dev.esteban.places.data.datasource.local.dao.PlaceDao
import dev.esteban.places.data.datasource.local.model.toEntity
import dev.esteban.places.data.datasource.local.model.toModel
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.places.domain.repository.PlaceRepository

class PlaceRepositoryImpl(
    private val placeLocalDataSource: PlaceLocalDataSource,
    private val placeDao: PlaceDao,
) : PlaceRepository {

    override suspend fun getVacationPlaces(): List<PlaceModel> {
        val placesSavedInDB = placeDao.getAllPlaces()
        return if (placesSavedInDB.isEmpty()) {
            val placesFromRaw = placeLocalDataSource.getVacationPlaces()
            placeDao.insertPlaces(placesFromRaw.map { it.toEntity() })
            placesFromRaw.map { it.toModel() }
        } else {
            placesSavedInDB.map { it.toModel() }
        }
    }
}
