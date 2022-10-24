package dev.esteban.places.data.datasource.local.model

import com.google.gson.annotations.SerializedName
import dev.esteban.places.domain.model.PlaceModel

data class PlaceResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("label")
    val label: String,
    @SerializedName("lat_lng")
    val latLng: List<Double>,
    @SerializedName("visited")
    val visited: Boolean,
    @SerializedName("description")
    val description: String,
)

fun PlaceResponse.toEntity() = PlaceEntity(
    label = label,
    lat = if (latLng.size > 1) latLng[0] else 0.0,
    lng = if (latLng.size == 2) latLng[1] else 0.0,
    visited = visited,
    description = description
)

fun PlaceResponse.toModel() = PlaceModel(
    id = id,
    label = label,
    latLng = latLng,
    visited = visited,
    description = description
)
