package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repaircalculator.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM Users WHERE login=:login AND password=:password LIMIT 1")
    suspend fun getUser(login: String, password: String): User?

    @Insert
    suspend fun insertUser(user: User): Long
}