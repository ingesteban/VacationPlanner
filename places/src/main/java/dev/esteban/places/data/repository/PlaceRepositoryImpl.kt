package dev.esteban.places.data.repository

import dev.esteban.places.data.datasource.local.PlaceLocalDataSource
import dev.esteban.places.data.datasource.local.dao.PlaceDao
import dev.esteban.places.data.datasource.local.model.toEntity
import dev.esteban.places.data.datasource.local.model.toModel
import dev.esteban.places.domain.model.PlaceModel
import dev.esteban.places.domain.repository.PlaceRepository
import java.lang.Exception

class PlaceRepositoryImpl(
    private val placeLocalDataSource: PlaceLocalDataSource,
    private val placeDao: PlaceDao,
) : PlaceRepository {
    override suspend fun getVacationPlaces(): List<PlaceModel> = try {
        val placesSavedInDB = placeDao.getAllPlaces()
        if (placesSavedInDB.isEmpty()) {
            val placesFromRaw = placeLocalDataSource.getVacationPlaces()
            placeDao.insertPlaces(placesFromRaw.map { it.toEntity() })
            placesFromRaw.map { it.toModel() }
        } else {
            placesSavedInDB.map { it.toModel() }
        }
    } catch (e: Exception) {
        throw e
    }

    override suspend fun updatePlaceVisited(id: Long, visited: Boolean) = try {
        placeDao.updatePlaceVisited(id = id, visited = visited)
    } catch (e: Exception) {
        throw e
    }

    override suspend fun deletePlace(id: Long) = try {
        placeDao.deletePlace(id = id)
    } catch (e: Exception) {
        throw e
    }
}
