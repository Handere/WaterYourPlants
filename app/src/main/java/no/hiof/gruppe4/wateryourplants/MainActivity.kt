package no.hiof.gruppe4.wateryourplants

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.hiof.gruppe4.wateryourplants.screen.LoginScreen
import no.hiof.gruppe4.wateryourplants.ui.home.*
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory


class MainActivity : ComponentActivity() {
    @RequiresApi(value = 26)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterYourPlantsTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)

                createNotificationChannel()
                showNotification(notifyingPlants())
            }
        }
    }

    private fun createNotificationChannel() {
        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.watering_notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(getString(R.string.watering_notification_channel_id), channelName, importance).apply {
                description = getString(R.string.watering_notification_channel_description)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun showNotification(numberOfNotifyingPlants: Int) {
        val notificationId = 1
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification
        val notification = NotificationCompat.Builder(applicationContext, getString(R.string.watering_notification_channel_id))
            .setContentTitle(getString(R.string.watering_notification_title_water_your_plants))
            .setContentText(getString(R.string.watering_notification_content_text_first_part)
                    + " " + numberOfNotifyingPlants
                    + " " + getString(R.string.watering_notification_content_text_second_part))
            .setSmallIcon(R.mipmap.water_your_plants_launcher_foreground)
            .build()

        // Send notification
        if (numberOfNotifyingPlants > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (notificationManager.areNotificationsEnabled()) {
                    notificationManager.notify(notificationId, notification)
                }
            }
            else {
                notificationManager.notify(notificationId, notification)
            }
        }
        else {
            notificationManager.cancel(notificationId)
        }
    }
}

@Composable
fun notifyingPlants(): Int {
    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))
    val allPlants by viewModel.allPlants.observeAsState(listOf())

    return viewModel.numberOfNotifyingPlants(allPlants)
}

@RequiresApi(value = 26)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.LoginScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LoginScreen.route) {
            LoginScreen(
                onNavigateToHomeScreen = { userName -> navController.navigate(Routes.HomeScreen.withArgs(userName)) }
            )
        }

        // Home screen
        composable(
            route = Routes.HomeScreen.withArgsFormat(Routes.HomeScreen.userName),
            arguments = listOf(
                navArgument(Routes.HomeScreen.userName) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments

            HomeScreen(
                onNavigateToRoom = { userName, plantRoomId -> navController.navigate(Routes.RoomScreen.withArgs(userName, plantRoomId.toString())) },
                onNavigateToCreatePlantRoom = {username -> navController.navigate(Routes.CreatePlantRoomScreen.withArgs(username))},
                userName = args?.getString(Routes.HomeScreen.userName)
            )
        }

        // Room screen
        composable(
            route = Routes.RoomScreen.withArgsFormat(Routes.RoomScreen.userName, Routes.RoomScreen.plantRoomId),
            arguments = listOf(
                navArgument(Routes.RoomScreen.userName) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(Routes.RoomScreen.plantRoomId) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments

            RoomScreen(
                onNavigationToCreatePlant = { userName, plantRoomId -> navController.navigate(Routes.CreatePlantScreen.withArgs(userName, plantRoomId.toString()))},
                onNavigationToPlantDetails = { userName, plantRoomId, plantId -> navController.navigate(Routes.PlantDetailsScreen.withArgs(userName, plantRoomId.toString(), plantId.toString()))},
                popBackStack = { navController.popBackStack() },
                userName = args?.getString(Routes.RoomScreen.userName),
                plantRoomId = args?.getInt(Routes.RoomScreen.plantRoomId)!!
            )
        }

        //Plant details screen
        composable(
            route = Routes.PlantDetailsScreen.withArgsFormat(
                Routes.PlantDetailsScreen.userName,
                Routes.PlantDetailsScreen.plantRoomId,
                Routes.PlantDetailsScreen.plantId),

            arguments = listOf(
                navArgument(Routes.PlantDetailsScreen.userName) {
                    type = NavType.StringType
                    nullable = true },
                navArgument(Routes.PlantDetailsScreen.plantRoomId) {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument(Routes.PlantDetailsScreen.plantId){
                    type = NavType.IntType
                    nullable = false
                }

            )

        ){ backStackEntry ->
            val args = backStackEntry.arguments

            PlantDetailsScreen(
                onNavigateToUpdatePlantScreen = { plantId, userName, plantRoomId -> navController.navigate(Routes.UpdatePlantScreen.withArgs(plantId.toString(), userName, plantRoomId.toString()))},
                popBackStack = { navController.popBackStack() },
                userName = args?.getString(Routes.PlantDetailsScreen.userName),
                plantRoomId = args?.getInt(Routes.PlantDetailsScreen.plantRoomId)!!,
                plantId = args?.getInt(Routes.PlantDetailsScreen.plantId)!!

            )
        }

        // Create plant screen
        composable(
            route = Routes.CreatePlantScreen.withArgsFormat(Routes.CreatePlantScreen.userName, Routes.CreatePlantScreen.plantRoomId),
            arguments = listOf(
                navArgument(Routes.CreatePlantScreen.userName) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(Routes.CreatePlantScreen.plantRoomId) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments

            CreatePlantScreen(
                userName = args?.getString(Routes.CreatePlantScreen.userName),
                plantRoomId = args?.getInt(Routes.CreatePlantScreen.plantRoomId)!!,
                popBackStack = { navController.popBackStack()})
        }

        // Update plant screen
        composable(
            route = Routes.UpdatePlantScreen.withArgsFormat(Routes.UpdatePlantScreen.plantId, Routes.UpdatePlantScreen.userName, Routes.UpdatePlantScreen.plantRoomId),
            arguments = listOf(
                navArgument(Routes.UpdatePlantScreen.plantId) {
                  type = NavType.IntType
                  nullable = false
                },
                navArgument(Routes.UpdatePlantScreen.userName) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(Routes.UpdatePlantScreen.plantRoomId) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments

            UpdatePlantScreen(
                plantId = args?.getInt(Routes.UpdatePlantScreen.plantId)!!,
                userName = args.getString(Routes.UpdatePlantScreen.userName),
                plantRoomId = args.getInt(Routes.UpdatePlantScreen.plantRoomId),
                popBackStack = { navController.popBackStack()})
        }

        // Create plant room screen
        composable(
            route = Routes.CreatePlantRoomScreen.withArgsFormat(Routes.CreatePlantRoomScreen.username),
            arguments = listOf(
                navArgument(Routes.CreatePlantRoomScreen.username) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments

            CreatePlantRoomScreen(
                username = args?.getString(Routes.CreatePlantRoomScreen.username),
                popBackStack = {navController.popBackStack()})
        }
    }
}


sealed class Routes(val route: String) {
    object LoginScreen : Routes("login_screen") {

    }
    object HomeScreen : Routes("home_screen") {
        const val userName = "userName"
    }
    object RoomScreen : Routes("room_screen") {
        const val userName = "userName"
        const val plantRoomId = "plantRoomId"
    }
    object CreatePlantScreen : Routes("create_plant_screen") {
        const val userName = "userName"
        const val plantRoomId = "plantRoomId"
    }
    object UpdatePlantScreen : Routes("update_plant_screen") {
        const val userName = "userName"
        const val plantRoomId = "plantRoomId"
        const val plantId = "plantId"
    }
    object CreatePlantRoomScreen: Routes("create_plant_room_screen") {
        const val username = "username"
    }
    object PlantDetailsScreen : Routes("plant_details_screen"){
        const val userName = "userName"
        const val plantRoomId = "plantRoomId"
        const val plantId = "plantId"
    }

    // Inspiration from https://github.com/vinchamp77/Demo_SimpleNavigationCompose
    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // Inspiration from https://github.com/vinchamp77/Demo_SimpleNavigationCompose
    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        // WaterYourPlantsApp()

}
