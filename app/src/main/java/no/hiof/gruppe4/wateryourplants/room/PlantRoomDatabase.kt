package no.hiof.gruppe4.wateryourplants.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(PlantEntity::class)], version = 1)
abstract class PlantRoomDatabase: RoomDatabase() {

    abstract fun plantDao(): PlantDao

    companion object {

        private var INSTANCE: PlantRoomDatabase? = null

        fun getInstance(context: Context): PlantRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlantRoomDatabase::class.java,
                        "plants_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}