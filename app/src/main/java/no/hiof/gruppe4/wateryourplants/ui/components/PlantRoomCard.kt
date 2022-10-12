package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

@Composable
fun PlantRoomCard(
    onNavigationToRoom: (String, Int) -> Unit,
    userName: String?,
    buttonName: String?,
    plantRoomId: Int,
    modifier: Modifier = Modifier) {
    Button(onClick = { onNavigationToRoom(userName.toString(), plantRoomId) },
        shape = Shapes.medium,
        modifier = modifier.fillMaxWidth()) {
        buttonName?.let { Text(text = it.uppercase()) }
    }
}