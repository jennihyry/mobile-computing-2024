package com.example.mobicomp2024
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val userName: String,
    @PrimaryKey(autoGenerate=true)
    val id: Int = 0
)