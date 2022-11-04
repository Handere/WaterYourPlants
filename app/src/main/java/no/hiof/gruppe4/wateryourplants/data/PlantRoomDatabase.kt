package no.hiof.gruppe4.wateryourplants.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import no.hiof.gruppe4.wateryourplants.R
import java.sql.Date
import java.time.LocalDate

@Database(entities = [(Plant::class), (PlantRoom::class)], version = 1)
@TypeConverters(Converters::class)
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

        // TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
        @RequiresApi(Build.VERSION_CODES.O)
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
                        "",
                       Date.valueOf(LocalDate.now().toString()),
                       Date.valueOf(LocalDate.now().plusDays(10).toString()))
                        )

                   plantDao.insertPlant(Plant(
                        1,
                        "Julestjerne",
                        "Euphorbia pulcherrima",
                        "Blomsterplanter",
                        R.drawable.no_plant_image,
                        0,
                        0,
                       "tirsdag",
                        "lite",
                        "",
                       Date.valueOf(LocalDate.now().toString()),
                       Date.valueOf(LocalDate.now().plusDays(0).toString()))
                        )

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
                            "",
                            Date.valueOf(LocalDate.now().toString()),
                            Date.valueOf(LocalDate.now().plusDays(5).toString()))
                    )

                    plantDao.insertPlant(
                        Plant(
                            2,
                            "Orkide",
                            "Orchidaceae",
                            "Blomsterplanter",
                            R.drawable.no_plant_image,
                            29,
                            29,
                            "tirsdag",
                            "halvskygge - sol",
                            "",
                            Date.valueOf(LocalDate.now().toString()),
                            Date.valueOf(LocalDate.now().plusDays(29).toString()))
                    )

                    plantDao.insertPlant(
                        Plant(
                            2,
                            "Stuegran",
                            "Araucaria heterophylla",
                            "Bartrær",
                            R.drawable.no_plant_image,
                            4,
                            4,
                            "tirsdag",
                            "halvskygge - sol",
                            "",
                            Date.valueOf(LocalDate.now().minusDays(94).toString()),
                            Date.valueOf(LocalDate.now().plusDays(29).toString()))
                    )

                    plantDao.insertPlant(
                        Plant(
                            2,
                            "Draketre",
                            "Dracaena draco",
                            "Blomsterplanter",
                            R.drawable.no_plant_image,
                            4,
                            4,
                            "tirsdag",
                            "halvskygge - sol",
                            "",
                            Date.valueOf(LocalDate.now().minusDays(94).toString()),
                            Date.valueOf(LocalDate.now().toString()))
                    )
                }
            }
        }
    }
}

