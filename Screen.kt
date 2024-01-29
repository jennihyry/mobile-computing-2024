package com.example.mobicomp2024


sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object UserScreen: Screen("user_screen")

}