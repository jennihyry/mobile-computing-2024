package com.example.mobicomp2024

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    // Functions to modify our table
    @Upsert
    suspend fun upsertUser(user: User)

    @Query("SELECT userName FROM User ORDER BY id DESC LIMIT 1")
    fun loadUserName(): String

    @Query("SELECT (SELECT COUNT(*) FROM User) == 0")
    fun isEmpty(): Boolean
}