package dev.esteban.places.domain.model

data class PlaceModel(
    val id: Long,
    val label: String,
    val latLng: List<Double>,
    val visited: Boolean,
    val description: String,
)
