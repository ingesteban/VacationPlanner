package dev.esteban.places.data.datasource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.esteban.places.data.datasource.local.dao.PlaceDao
import dev.esteban.places.data.datasource.local.model.PlaceEntity


private const val PLACE_DB_VERSION = 1
private const val PLACE_DB = "place.db"

@Database(
    entities = [PlaceEntity::class],
    version = PLACE_DB_VERSION,
    exportSchema = false
)
abstract class PlaceRoomDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context, PlaceRoomDatabase::class.java, PLACE_DB)
                .fallbackToDestructiveMigration()
                .build()
    }
}
