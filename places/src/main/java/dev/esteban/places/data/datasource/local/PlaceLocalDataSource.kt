package dev.esteban.places.data.datasource.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.esteban.places.data.datasource.local.model.PlaceResponse
import java.lang.reflect.Type

class PlaceLocalDataSource(val context: Context) {

    private companion object {
        const val VACATIONS_PLANNER = "vacations_planner.json"
    }

    fun getVacationPlaces(): List<PlaceResponse> {
        val vacationsPlannerJson =
            context.assets.open(VACATIONS_PLANNER).bufferedReader().use { it.readText() }
        val listType: Type = object : TypeToken<List<PlaceResponse>>() {}.type
        return Gson().fromJson(vacationsPlannerJson, listType)
    }
}