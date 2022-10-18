package no.hiof.gruppe4.wateryourplants.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory

@Composable
fun CreatePlantRoomScreen(
    username: String?,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mContext = LocalContext.current
    val plantRoomName = remember { mutableStateOf(TextFieldValue()) }
    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))


    Scaffold(
        topBar = { ScaffoldTopAppBar(userName = username)},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val errorMessage = "All fields are required"
                if (plantRoomName.value.text.isEmpty()) {
                    Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show()
                }
                else {
                    viewModel.insertPlantRoom(plantRoomName.value.text)
                    popBackStack()
                }
            })
            {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
            }
        }
    ) {padding -> // TODO: yep....
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(id = R.string.add_new_room_title), fontSize = 30.sp)
            TextField(
                label ={ Text(text = stringResource(id = R.string.add_new_room_name)) },
                value = plantRoomName.value,
                onValueChange = { plantRoomName.value = it }
            )
        }
    }
}