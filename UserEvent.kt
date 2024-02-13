package com.example.mobicomp2024

sealed interface UserEvent {
    object SaveUser: UserEvent
    data class SetUserName(val userName: String): UserEvent
    object LoadUserName: UserEvent
}