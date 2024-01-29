package com.example.mobicomp2024

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            // This block defines the main screen
            MainScreen(navController)
        }
        composable(
            route = Screen.UserScreen.route,
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "user"
                }
            )
        ){
            // Here comes another screen
            UserScreen(navController)
        }
    }

}


