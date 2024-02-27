package com.example.mobicomp2024

import android.content.Context
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


var username = "Jenni"
var imageId = R.drawable.android_image

@Composable
fun MainScreen(navController: NavController,
               state: UserState,
               onEvent: (UserEvent) -> Unit,
               context: Context
) {
    // Here comes the code that defines what our main screen looks like. Because we want
    // the conversation to be the main screen, lets call the function conversation
    Column(modifier=Modifier.padding(all=8.dp), horizontalAlignment = Alignment.End) {

        IconButton(
            onClick = {
                navController.navigate(route = "user_screen" + "/$username" + "/$imageId"){
                    popUpTo(Screen.UserScreen.route) {
                        inclusive = true
                    }
                }
            }

            ){
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Button",
                modifier = Modifier.size(25.dp)
            )
        }

        Button(
            onClick = {
                // send notification
                NotificationUtils.sendNotification(context, "uujee", "jee")
            })
        {
            Text("Send notification")
        }

        val uri = getImageUriFromStorage(context = context)

        onEvent(UserEvent.LoadUserName) // loads the latest db entry username to state
        Conversation(SampleData.conversationSample, state, uri)

    }
}

data class Message(var author: String, val content: String)

@Composable
fun MessageCard(msg: Message, uri: Uri?) {
    // Padding around message
    Row(modifier=Modifier.padding(all=8.dp)){

        AsyncImage(
            model = uri ?: R.drawable.android_image,
            error = painterResource(R.drawable.android_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        // Horizontal space between image and text
        Spacer(modifier = Modifier.width(8.dp))

        // Variable to keeping track of expanded messages
        var isExpanded by remember { mutableStateOf(false) }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )


        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }){
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            // Vertical space between the author and message
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.content,
                    modifier = Modifier.padding(all = 4.dp),
                    // If message is expanded, display all its content, else only the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>, state: UserState, uri: Uri?) {
    LazyColumn {
        items(messages) { message ->
            message.author = state.userName
            MessageCard(message, uri)
        }
    }
}


fun getImageUriFromStorage(context: Context): Uri? {
    try {
        val outputDir = File(context.filesDir, "image_data")
        val file = File(outputDir, "selected_image_uri.txt")
        if (file.exists()) {
            val bufferedReader = BufferedReader(FileReader(file))
            val imageUriString = bufferedReader.readLine()
            bufferedReader.close()
            return Uri.parse(imageUriString)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}
