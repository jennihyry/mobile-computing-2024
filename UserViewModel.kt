package com.example.mobicomp2024

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val dao: UserDao
): ViewModel(){

    val state = MutableStateFlow(UserState())

    fun onEvent(event: UserEvent){

        when(event){
            UserEvent.SaveUser -> {

                val userName = state.value.userName

                val user = User(
                    userName = userName
                )
                viewModelScope.launch{
                    dao.upsertUser(user)
                }
                state.update{ it.copy(
                    userName = ""
                )}
            }
            is UserEvent.SetUserName -> {
                state.update{ it.copy(
                        userName = event.userName
                ) }
            }

            UserEvent.LoadUserName -> {
                // If the database is empty, load default username
                if (dao.isEmpty()){
                    state.update{ it.copy(
                        userName = "username"
                    )}
                } else {
                    state.update {
                        it.copy(
                            userName = dao.loadUserName()
                        )
                    }
                }
            }
        }
    }

}