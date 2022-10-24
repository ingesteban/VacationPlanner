package dev.esteban.vacationplanner.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.esteban.places.domain.model.PlaceModel

class PlaceModelParamType : NavType<PlaceModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PlaceModel? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PlaceModel {
        val myType = object : TypeToken<PlaceModel>() {}.type
        return Gson().fromJson(value, myType)
    }

    override fun put(bundle: Bundle, key: String, value: PlaceModel) {
        bundle.putParcelable(key, value)
    }
}
