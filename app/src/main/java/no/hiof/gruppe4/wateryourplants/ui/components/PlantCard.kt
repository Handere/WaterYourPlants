package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.ImageResult
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes
import java.sql.Date
import java.time.LocalDate

// TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
@RequiresApi(value = 26)
@Composable
fun PlantCard(
    onNavigationToPlantDetails: (String, Int, Int) -> Unit,
    userName: String?,
    plantRoomId: Int,
    plantId: Int,
    photoId: String,
    contentDescription: String,
    species: String,
    speciesLatin: String,
    nextWateringDay: Date,
    modifier: Modifier = Modifier,
) {
    var usePhoto: String
    if(photoId != ""){
        usePhoto = photoId
    }

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
val request =


                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current.applicationContext)
                        .error(R.drawable.no_plant_image)
                        .data(photoId)
                        .build(),
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
                    Row(modifier = modifier.fillMaxWidth()) {
                        Text(text = "Water: " + nextWateringDay.toString(),
                            fontSize = 14.sp,
                            modifier = modifier.padding(5.dp),
                            fontStyle = FontStyle.Italic
                        )
                        if (nextWateringDay.compareTo(Date.valueOf(LocalDate.now().toString())) <= 0){
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                        }
                    }
                }
            }
        }
    }
}