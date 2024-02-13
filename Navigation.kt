package com.example.mobicomp2024

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(state: UserState,
               onEvent: (UserEvent) -> Unit,
               context: Context
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            // This block defines the main screen
            MainScreen(navController, state = state, onEvent = onEvent, context = context)
        }
        composable(
            route = Screen.UserScreen.route,
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "default user"
                    nullable = true
                },
                navArgument("imageId"){
                    type = NavType.IntType
                    defaultValue = R.drawable.android_image
                    nullable = false
                }
            )
        ){ entry ->
            // Here comes another screen
            val name = entry.arguments?.getString("name")
            val imageId = entry.arguments!!.getInt("imageId")
            UserScreen(navController,
                name = name,
                imageId = imageId,
                state = state,
                onEvent = onEvent,
                context = context)
        }
    }

}


