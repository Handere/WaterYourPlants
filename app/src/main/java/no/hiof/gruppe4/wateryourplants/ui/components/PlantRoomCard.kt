package no.hiof.gruppe4.wateryourplants.ui.components

import android.content.Intent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import no.hiof.gruppe4.wateryourplants.ui.home.RoomActivity

@Composable
fun PlantRoomCard(
    buttonName: String, 
    modifier: Modifier = Modifier) {
    val localContext = LocalContext.current
    Button(onClick = {localContext.startActivity(Intent(localContext, RoomActivity::class.java))}) {
        Text(text = buttonName)
    }
}