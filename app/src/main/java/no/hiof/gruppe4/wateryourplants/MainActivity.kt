package no.hiof.gruppe4.wateryourplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.hiof.gruppe4.wateryourplants.screen.LoginScreen
import no.hiof.gruppe4.wateryourplants.ui.home.CreatePlantScreen
import no.hiof.gruppe4.wateryourplants.ui.home.HomeScreen
import no.hiof.gruppe4.wateryourplants.ui.home.RoomScreen
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterYourPlantsTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)

                /*val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: PlantViewModel = viewModel(
                        it,
                        "PlantViewModel",
                        PlantViewModelFactory(LocalContext.current.applicationContext as Application)
                    )
                    ScreenSetup(viewModel)
                }*/
            }
        }
    }
}

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
                userName = args?.getString(Routes.RoomScreen.userName),
                plantRoomId = args?.getInt(Routes.RoomScreen.plantRoomId)!!
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
                plantRoomId = args?.getInt(Routes.CreatePlantScreen.plantRoomId)!!)
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
