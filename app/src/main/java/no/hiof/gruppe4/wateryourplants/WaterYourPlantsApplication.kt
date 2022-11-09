package no.hiof.gruppe4.wateryourplants

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import no.hiof.gruppe4.wateryourplants.data.PlantRepository
import no.hiof.gruppe4.wateryourplants.data.PlantRoomDatabase

class WaterYourPlantsApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PlantRoomDatabase.getInstance(this, applicationScope) }

    val repository by lazy { PlantRepository(database.plantDao()) }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationChannel = NotificationChannel(
                "water_channel",
                "Watering reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}