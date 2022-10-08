package no.hiof.gruppe4.wateryourplants.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import no.hiof.gruppe4.wateryourplants.Routes
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

@Composable
fun PlantRoomCard(
    onNavigationToRoom: () -> Unit,
    buttonName: String, 
    modifier: Modifier = Modifier) {
    Button(onClick = onNavigationToRoom,
        shape = Shapes.medium,
        modifier = modifier.fillMaxWidth()) {
        Text(text = buttonName)
    }
}