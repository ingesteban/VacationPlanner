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

val h4Typography: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal,
    color = Color.Black
)

val h5Typography: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    color = Color.Black
)

data class VacationPlannerTypography(
    val h1: TextStyle = h1Typography,
    val h2: TextStyle = h2Typography,
    val h3: TextStyle = h3Typography,
    val h4: TextStyle = h4Typography,
    val h5: TextStyle = h5Typography,
)

internal val LocalVacationPlannerTypography =
    staticCompositionLocalOf { VacationPlannerTypography() }
