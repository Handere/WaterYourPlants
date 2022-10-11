package no.hiof.gruppe4.wateryourplants

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import no.hiof.gruppe4.wateryourplants.room.PlantRepository
import no.hiof.gruppe4.wateryourplants.room.PlantRoomDatabase

class WaterYourPlantsApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PlantRoomDatabase.getInstance(this, applicationScope) }

    val repository by lazy { PlantRepository(database.plantDao()) }
}