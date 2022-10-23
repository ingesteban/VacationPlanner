package dev.esteban.vacationplanner.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

data class VacationPlannerColors(
    val purple200: Color = Purple200,
    val purple500: Color = Purple500,
    val purple700: Color = Purple700,
    val teal200: Color = Teal200,
)

val LocalVacationPlannerColors = staticCompositionLocalOf { VacationPlannerColors() }
