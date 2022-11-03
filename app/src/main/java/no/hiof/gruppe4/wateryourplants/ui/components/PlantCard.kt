package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes
import java.sql.Date

@Composable
fun PlantCard(
    onNavigationToPlantDetails: (String, Int, Int) -> Unit,
    userName: String?,
    plantRoomId: Int,
    plantId: Int,
    painter: Painter = painterResource(id = R.drawable.no_plant_image), // TODO: Change to photo from db
    contentDescription: String,
    species: String,
    speciesLatin: String,
    nextWateringDay: Date,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigationToPlantDetails(userName.toString(), plantRoomId, plantId) }
            .padding(0.dp, 0.dp, 0.dp, 5.dp),
        shape = Shapes.medium,
        elevation = 5.dp,) {

        Box(modifier = modifier
            .height(200.dp)) {
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxWidth(0.5f))
                Column(modifier = modifier
                        .fillMaxHeight()) {
                    Text(text = species,
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = speciesLatin,
                        fontSize = 14.sp,
                        modifier = modifier.padding(5.dp),
                        fontStyle = FontStyle.Italic
                    )
                    Text(text = "Water: " + nextWateringDay.toString(),
                        fontSize = 14.sp,
                        modifier = modifier.padding(5.dp),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}