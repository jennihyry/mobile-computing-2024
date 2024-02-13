package com.example.mobicomp2024


sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object UserScreen: Screen("user_screen" + "/{name}" + "/{image_id}")

    fun withArgs(vararg args: String): String{
        return buildString{
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }

}