package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.R

@Composable
fun PlantCard(
    painter: Painter = painterResource(id = R.drawable.no_plant_image),
    contentDescription: String,
    plantName: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable {  },
        shape = RoundedCornerShape(15.dp),
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
                    contentScale = ContentScale.Crop)
                Text(text = plantName,
                    modifier
                        .padding(12.dp),
                    fontSize = 16.sp)
            }

        }
    }

}