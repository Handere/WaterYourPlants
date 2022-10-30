package no.hiof.gruppe4.wateryourplants

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import no.hiof.gruppe4.wateryourplants.screen.LoginScreen
import no.hiof.gruppe4.wateryourplants.ui.home.CreatePlantRoomScreen
import no.hiof.gruppe4.wateryourplants.ui.home.CreatePlantScreen
import no.hiof.gruppe4.wateryourplants.ui.home.HomeScreen
import no.hiof.gruppe4.wateryourplants.ui.home.PlantDetailsScreen
import no.hiof.gruppe4.wateryourplants.ui.home.RoomScreen
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        createAuthenticationListener()

        Log.d("AuthUser", "User: " + auth.currentUser?.email)

        setContent {
            WaterYourPlantsTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }

    private fun createAuthenticationListener() {
        authStateListener = FirebaseAuth.AuthStateListener {
            val firebaseUser = auth.currentUser
            if (firebaseUser == null) {
                createSignInIntent()
            }
            else {
                Toast.makeText(this, "You are signed in as: " + Firebase.auth.currentUser?.displayName, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun createSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...
        } else {
            // TODO: Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
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
