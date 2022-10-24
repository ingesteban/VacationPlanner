package dev.esteban.places.data.datasource.local

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import dev.esteban.places.R
import dev.esteban.places.data.datasource.local.model.PlaceResponse

class PlaceLocalDataSource {

    fun getVacationPlaces(): List<PlaceResponse> {
        val inputStream: InputStream =
            Resources.getSystem().openRawResource(R.raw.vacations_planner)
        val streamReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
        val responseStrBuilder = StringBuilder()
        var inputStr: String?
        while (streamReader.readLine().also { inputStr = it } != null) responseStrBuilder.append(
            inputStr
        )
        val listType: Type = object : TypeToken<List<PlaceResponse>>() {}.type
        return Gson().fromJson(inputStr, listType)
    }
}