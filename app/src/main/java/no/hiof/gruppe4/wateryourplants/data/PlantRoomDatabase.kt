package no.hiof.gruppe4.wateryourplants.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import no.hiof.gruppe4.wateryourplants.R

@Database(entities = [(Plant::class), (PlantRoom::class)], version = 1)
abstract class PlantRoomDatabase: RoomDatabase() {

    abstract fun plantDao(): PlantDao

    companion object {

        private var INSTANCE: PlantRoomDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): PlantRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it}
            }
        }

        private fun buildDatabase(context: Context, scope: CoroutineScope): PlantRoomDatabase {
            return Room.databaseBuilder(context, PlantRoomDatabase::class.java, "plant_database")
                .addCallback(PlantDatabaseCallback(scope))
                .build()
        }
    }
    private class PlantDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val plantDao = database.plantDao()



                    // Delete all content here.
                    /*movieDao.deleteAll()*/

                    // Add sample words.
                    plantDao.insertPlantRoom(
                        PlantRoom("stue")
                    )
                    plantDao.insertPlantRoom(
                        PlantRoom("kjøkken")
                    )

                   plantDao.insertPlant(Plant(
                        1,
                        "Monstera",
                        "Monstera deliciosa",
                        "Blomsterplanter",
                        R.drawable.no_plant_image,
                        10,
                        10,
                       "tirsdag",
                        "lite",
                        "") )

                    plantDao.insertPlant(
                        Plant(2,
                            "Bjørkefiken",
                             "Ficus benjamina 'Danielle'",
                            "Blomsterplanter",

                            R.drawable.no_plant_image,
                            5,
                            5,
                            "tirsdag",
                            "halvskygge",
                            "")
                    )

                    plantDao.insertPlant(
                        Plant(
                            2,
                            "Orkide",
                            "Orchidaceae",
                            "Blomsterplanter",
                            R.drawable.no_plant_image,
                            5,
                            5,
                            "tirsdag",
                            "halvskygge - sol",
                            "")
                    )
                }
            }
        }
    }
}

