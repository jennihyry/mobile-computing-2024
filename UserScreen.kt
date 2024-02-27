package com.example.mobicomp2024

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
//import androidx.core.content.ContentProviderCompat.requireContext


@Composable
fun UserScreen(
    navController: NavController,
    name: String?,
    imageId: Int,
    state: UserState,
    onEvent: (UserEvent) -> Unit,
    context: Context
) {

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    if (selectedImageUri == null) {
        selectedImageUri = getImageUriFromStorage(context=context)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Column(modifier=Modifier.padding(all=8.dp)) {

        IconButton(
            onClick = {
                onEvent(UserEvent.SaveUser)
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.MainScreen.route) {
                        inclusive = true
                    }
                }
            }

        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Button",
                modifier = Modifier.size(25.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = selectedImageUri,
            error = painterResource(id=imageId),
            contentDescription = "Contact profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            })
        {
            Text("Load new image")
        }

        loadImageUriToStorage(context=context, uri= selectedImageUri)

        Spacer(modifier = Modifier.height(10.dp))


        TextField(
            value = state.userName,
            onValueChange = {
                onEvent(UserEvent.SetUserName(it))
            }
        )
    }
}

fun loadImageUriToStorage(context: Context, uri: Uri?) {
    val outputDir = File(context.filesDir, "image_data")
    if (!outputDir.exists()) {
        outputDir.mkdirs()
    }
    try {
        val outputFile = File(outputDir, "selected_image_uri.txt")
        val outputStream = FileOutputStream(outputFile)
        outputStream.write(uri.toString().toByteArray())
        outputStream.close()
        println("File write successful")
    } catch (e: FileNotFoundException){
        e.printStackTrace()
    } catch (e: IOException){
        e.printStackTrace()
    }
}