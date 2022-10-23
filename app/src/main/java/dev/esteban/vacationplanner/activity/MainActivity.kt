package dev.esteban.vacationplanner.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.esteban.vacationplanner.VacationPlannerApp
import dev.esteban.vacationplanner.ui.theme.VacationPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VacationPlannerTheme {
                VacationPlannerApp()
            }
        }
    }
}
