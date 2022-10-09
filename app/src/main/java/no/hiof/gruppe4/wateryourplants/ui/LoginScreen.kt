package no.hiof.gruppe4.wateryourplants.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

// Code inspiration from: https://dev.to/manojbhadane/android-login-screen-using-jetpack-compose-part-1-50pl

    @Composable
    fun LoginScreen(
        onNavigateToHomeScreen: (String) -> Unit,
        painter: Painter = painterResource(id = R.drawable.water_your_plants)) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val username = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }

            // Picture designed by Freepik, modified by Handere
            Image(painter = painter,
                contentDescription = stringResource(id = R.string.water_your_plants),
                contentScale = ContentScale.Crop)

            // Text(text = "Login", style = TextStyle(fontSize = 40.sp))

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Username") },
                value = username.value,
                onValueChange = { username.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Password") },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = { onNavigateToHomeScreen("TestUsername") }, //TODO: Change to actual username
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
                onClick = { },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default
                )
            )
        }
    }
