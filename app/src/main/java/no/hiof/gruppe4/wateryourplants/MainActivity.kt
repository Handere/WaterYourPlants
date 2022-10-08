package no.hiof.gruppe4.wateryourplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.hiof.gruppe4.wateryourplants.screen.LoginScreen
import no.hiof.gruppe4.wateryourplants.ui.home.HomeScreen
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterYourPlantsTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login_screen"
                ) {
                    composable(Routes.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }
                    composable(Routes.HomeScreen.route) {
                        HomeScreen(navController = navController)
                    }
                }
/*
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // WaterYourPlantsApp()
                }

 */
            }
        }
    }
}

sealed class Routes(val route: String) {
    object LoginScreen : Routes("login_screen")
    object HomeScreen : Routes("home_screen")
    object RoomScreen : Routes("room_screen")
    object CreatePlantScreen : Routes("create_plant_screen")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        // WaterYourPlantsApp()

}
