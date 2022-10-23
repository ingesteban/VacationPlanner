package dev.esteban.vacationplanner.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun VacationPlannerTheme(
    colors: VacationPlannerColors = VacationPlannerTheme.color,
    shapes: VacationPlannerShapes = VacationPlannerTheme.shapes,
    typography: VacationPlannerTypography = VacationPlannerTheme.typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalVacationPlannerColors provides colors,
        LocalVacationPlannerTypography provides typography,
        LocalVacationPlannerShapes provides shapes
    ) {
        MaterialTheme(
            colors = LightColorPalette,
            content = content
        )
    }
}

object VacationPlannerTheme {
    val color: VacationPlannerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalVacationPlannerColors.current

    val shapes: VacationPlannerShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalVacationPlannerShapes.current

    val typography: VacationPlannerTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalVacationPlannerTypography.current
}
