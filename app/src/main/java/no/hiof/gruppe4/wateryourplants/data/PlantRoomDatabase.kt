package no.hiof.gruppe4.wateryourplants.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.model.plantRoom
import java.sql.Date

@Database(entities = [(Plant::class)], version = 1)
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
                    plantDao.insertPlantRoomWithPlants(
                        PlantRoomWithPlants(
                            PlantRoom("Soverom"),
                            listOf(
                                Plant(
                                    0,
                                    "Monstera",
                                    "Monstera deliciosa",
                                    "Blomsterplanter",
                                    //Date(2008-11-11),
                                    R.drawable.no_plant_image,
                                    10,
                                    10,
                                    "Tirsdag",
                                    "lite",
                                    ""),
                            Plant(0,
                                "Bjørkefiken",
                                "Ficus benjamina 'Danielle'",
                                "Blomsterplanter",
                                //Date(2008-11-11),
                                R.drawable.no_plant_image,
                                5,
                                5,
                                "Tirsdag",
                                "halvskygge",
                                ""),
                            Plant(
                                0,
                                "Orkide",
                                "Orchidaceae",
                                "Blomsterplanter",
                                //Date(2008-11-11),
                                R.drawable.no_plant_image,
                                5,
                                5,
                                "Tirsdag",
                                "halvskygge - sol",
                                "")
                            )
                        )
                    )



/*
                   /plantDao.insertPlant(Plant(
                        "Monstera",
                        "Monstera deliciosa",
                        "Blomsterplanter",
                        2,
                        R.drawable.no_plant_image,
                        10,
                        10,
                        "lite",
                        "") )

                    plantDao.insertPlant(
                        Plant("Bjørkefiken",
                             "Ficus benjamina 'Danielle'",
                            "Blomsterplanter",
                             2,
                            R.drawable.no_plant_image,
                            5,
                            5,
                            "halvskygge",
                            "")
                    )

                    plantDao.insertPlant(
                        Plant(
                            "Orkide",
                            "Orchidaceae",
                            "Blomsterplanter",
                        1,
                        R.drawable.no_plant_image,
                        5,5,
                        "halvskygge - sol",
                        "")
                    )

 */
                }
            }
        }
    }
}

