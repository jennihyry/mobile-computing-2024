package com.example.mobicomp2024

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun UserScreen(navController: NavController) {
    Column(modifier=Modifier.padding(all=8.dp)) {

        IconButton(
            onClick = {
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

        Text(text = "This is another screen")
    }
}