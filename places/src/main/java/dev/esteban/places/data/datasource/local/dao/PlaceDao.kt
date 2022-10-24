package dev.esteban.places.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.esteban.places.data.datasource.local.model.PlaceEntity

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(placesEntity: List<PlaceEntity>): List<Long>

    @Query("Select * from Place")
    suspend fun getAllPlaces(): List<PlaceEntity>
}