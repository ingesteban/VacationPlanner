package dev.esteban.vacationplanner.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val h1Typography: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
    color = Color.Black
)

val h2Typography: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 12.sp,
    fontWeight = FontWeight.Normal,
    color = Color.Gray
)

val h3Typography: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 22.sp,
    fontWeight = FontWeight.Bold,
    color = Color.White
)

data class VacationPlannerTypography(
    val h1: TextStyle = h1Typography,
    val h2: TextStyle = h2Typography,
    val h3: TextStyle = h3Typography
)

internal val LocalVacationPlannerTypography =
    staticCompositionLocalOf { VacationPlannerTypography() }
