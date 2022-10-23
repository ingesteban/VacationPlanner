package dev.esteban.vacationplanner.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class VacationPlannerShapes(
    val small: RoundedCornerShape = RoundedCornerShape(2.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(4.dp),
    val large: RoundedCornerShape = RoundedCornerShape(8.dp)
)

val LocalVacationPlannerShapes = staticCompositionLocalOf { VacationPlannerShapes() }