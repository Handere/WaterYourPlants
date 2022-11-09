package no.hiof.gruppe4.wateryourplants

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.hiof.gruppe4.wateryourplants.screen.LoginScreen
import no.hiof.gruppe4.wateryourplants.ui.home.*
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme
import no.hiof.gruppe4.wateryourplants.R


class MainActivity : ComponentActivity() {
    @RequiresApi(value = 26)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterYourPlantsTheme {
                val context = LocalContext.current
                var hasNotificationPermission = remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    } else mutableStateOf(true)
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        hasNotificationPermission = mutableStateOf(isGranted)

                    }
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    SideEffect {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                if(hasNotificationPermission.value) {
                    showNotification()
                }

                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
        showNotification()
    }

    private fun showNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, "water_channel")
            .setContentTitle("Water your plant!")
            .setContentText("It's time to water your plant(s).")
            .setSmallIcon(R.drawable.no_plant_image)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (notificationManager.areNotificationsEnabled()) {
                    notificationManager.notify(1, notification)
                }
            }
    }
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
        val userName = "userName"
    }
    object RoomScreen : Routes("room_screen") {
        val userName = "userName"
        val plantRoomId = "plantRoomId"
    }
    object CreatePlantScreen : Routes("create_plant_screen") {
        val userName = "userName"
        val plantRoomId = "plantRoomId"
    }
    object CreatePlantRoomScreen: Routes("create_plant_room_screen") {
        val username = "username"
    }

    object PlantDetailsScreen : Routes("plant_details_screen"){
        val userName = "userName"
        val plantRoomId = "plantRoomId"
        val plantId = "plantId"
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
