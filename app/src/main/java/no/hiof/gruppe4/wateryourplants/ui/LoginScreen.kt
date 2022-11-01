package no.hiof.gruppe4.wateryourplants.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.screen.LoadingState
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

// Code inspiration from: https://dev.to/manojbhadane/android-login-screen-using-jetpack-compose-part-1-50pl
// and https://ericampire.com/firebase-auth-with-jetpack-compose

    @Composable
    fun LoginScreen(
        onNavigateToHomeScreen: (String) -> Unit,
        painter: Painter = painterResource(id = R.drawable.water_your_plants)) {

        val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))
        val mContext = LocalContext.current
        // val userEmail = remember { mutableStateOf(TextFieldValue("DefaultUserName")) } // Don't remove "DefaultUserName" without exception handling
        // val userPassword = remember { mutableStateOf(TextFieldValue()) }
        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        val state by viewModel.loadingState.collectAsState()

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state.status == LoadingState.Status.RUNNING) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            // Picture designed by Freepik, modified by Handere
            Image(painter = painter,
                contentDescription = stringResource(id = R.string.water_your_plants),
                contentScale = ContentScale.Crop)

            // Text(text = "Login", style = TextStyle(fontSize = 40.sp))

            // Username
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = stringResource(id = R.string.username)) },
                value = userEmail,
                onValueChange = { userEmail = it },
                singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Password
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = stringResource(id = R.string.password)) },
                value = userPassword,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { userPassword = it },
                singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { viewModel.signInWithEmailAndPassword(userEmail.trim(), userPassword.trim()) /*login(
                    onNavigateToHomeScreen = onNavigateToHomeScreen,
                    mContext = mContext,
                    username = userEmail) */})
            )

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = { viewModel.signInWithEmailAndPassword(userEmail.trim(), userPassword.trim()) /*login(
                        onNavigateToHomeScreen = onNavigateToHomeScreen,
                        mContext = mContext,
                        username = username)*/},
                    shape = Shapes.large,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.sign_up_here)) ,
                onClick = { /* TODO: Add functionality */ },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default
                )
            )

            // TODO: Running multiple times making it crash
            when(state.status) {
                LoadingState.Status.SUCCESS -> {
                    Text(text = "Success")
                    // login(onNavigateToHomeScreen, mContext, userEmail)
                    Log.d("login", "success")
                }
                LoadingState.Status.FAILED -> {
                    Text(text = state.msg ?: "Error")
                    Log.d("login", "failed")
                }
                else -> {}
            }
        }
    }

fun login(
    onNavigateToHomeScreen: (String) -> Unit,
    mContext: Context,
    username: String) {
    if (username.isEmpty()) {
        Toast.makeText(mContext, "Username can't be empty", Toast.LENGTH_SHORT).show()
    }
    else {
        onNavigateToHomeScreen(username)
    }
}

// Code from: https://ericampire.com/firebase-auth-with-jetpack-compose
data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE,
    }
}
