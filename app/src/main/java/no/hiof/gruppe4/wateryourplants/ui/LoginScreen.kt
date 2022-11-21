package no.hiof.gruppe4.wateryourplants.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WindowInfo
import no.hiof.gruppe4.wateryourplants.rememberWindowInfo
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

// Code inspiration from: https://dev.to/manojbhadane/android-login-screen-using-jetpack-compose-part-1-50pl

    @Composable
    fun LoginScreen(
        onNavigateToHomeScreen: (String) -> Unit,
        painter: Painter = painterResource(id = R.drawable.water_your_plants),
        modifier: Modifier = Modifier) {

        val mContext = LocalContext.current
        val username = remember { mutableStateOf(TextFieldValue("DefaultUserName")) } // Don't remove "DefaultUserName" without exception handling
        val password = remember { mutableStateOf(TextFieldValue()) }

        val windowInfo = rememberWindowInfo()

        Row(modifier = modifier.fillMaxWidth()) {
            if (windowInfo.screenWithInfo is WindowInfo.WindowType.Medium || windowInfo.screenWithInfo is WindowInfo.WindowType.Expanded) {
                // Picture designed by Freepik, modified by Handere
                Image(
                    painter = painter,
                    contentDescription = stringResource(id = R.string.water_your_plants),
                    modifier = modifier.fillMaxWidth(0.5f)
                )
            }

            LazyColumn(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (windowInfo.screenWithInfo is WindowInfo.WindowType.Compact) {
                    // Picture designed by Freepik, modified by Handere
                    item {
                        Image(painter = painter,
                            contentDescription = stringResource(id = R.string.water_your_plants),
                            modifier = modifier.fillMaxWidth())
                    }
                }
                else {
                    item {
                        Text(text = stringResource(id = R.string.login), fontSize = 30.sp)
                    }
                }

                // Username
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.username)) },
                        value = username.value,
                        onValueChange = { username.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                }

                // Password
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.password)) },
                        value = password.value,
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = { password.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { login(
                            onNavigateToHomeScreen = onNavigateToHomeScreen,
                            mContext = mContext,
                            username = username) })
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = { login(
                                onNavigateToHomeScreen = onNavigateToHomeScreen,
                                mContext = mContext,
                                username = username)},
                            shape = Shapes.large,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = stringResource(id = R.string.login))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.sign_up_here)) ,
                        onClick = { /* TODO: Add functionality */ },
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default
                        )
                    )
                }
            }
        }
    }

fun login(
    onNavigateToHomeScreen: (String) -> Unit,
    mContext: Context,
    username: MutableState<TextFieldValue>) {
    if (username.value.text.isEmpty()) {
        Toast.makeText(mContext, "Username can't be empty", Toast.LENGTH_SHORT).show()
    }
    else {
        onNavigateToHomeScreen(username.value.text)
    }
}