package dev.esteban.places.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.esteban.places.domain.model.PlaceModel

private const val PLACE = "Place"

@Entity(tableName = PLACE)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lng") val lng: Double,
    @ColumnInfo(name = "visited") val visited: Boolean,
    @ColumnInfo(name = "description") val description: String,
)

fun PlaceEntity.toModel() = PlaceModel(
    id = id,
    label = label,
    latLng = listOf(lat, lng),
    visited = visited,
    description = description,
)
