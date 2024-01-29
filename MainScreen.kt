package com.example.mobicomp2024

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    // Here comes the code that defines what our main screen looks like. Because we want
    // the conversation to be the main screen, lets call the function conversation
    Column(modifier=Modifier.padding(all=8.dp), horizontalAlignment = Alignment.End) {

        IconButton(
            onClick = {
                navController.navigate(Screen.UserScreen.route){
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

        Conversation(SampleData.conversationSample)
    }
}