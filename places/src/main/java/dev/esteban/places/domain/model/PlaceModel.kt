package dev.esteban.places.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceModel(
    val id: Long,
    val label: String,
    val latLng: List<Double>,
    val visited: Boolean,
    val description: String,
) : Parcelable
