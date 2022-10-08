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
import no.hiof.gruppe4.wateryourplants.ui.home.HomeScreen
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterYourPlantsTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
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
                onNavigateToRoom = { navController.navigate(Routes.RoomScreen.route) },
                userName = args?.getString(Routes.HomeScreen.userName)
            )
        }
    }
}


sealed class Routes(val route: String) {
    object LoginScreen : Routes("login_screen")
    object HomeScreen : Routes("home_screen") {
        val userName = "userName"
    }
    object RoomScreen : Routes("room_screen")
    object CreatePlantScreen : Routes("create_plant_screen")

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
