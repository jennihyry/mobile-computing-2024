package com.example.mobicomp2024

data class UserState(
    val users: List<User> = emptyList(),
    val userName: String = ""
)
