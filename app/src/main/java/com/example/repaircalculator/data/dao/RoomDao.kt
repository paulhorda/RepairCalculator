package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repaircalculator.data.entity.Room

@Dao
interface RoomDao {
//    @Query("SELECT * FROM Rooms WHERE login=:login AND password=:password LIMIT 1")
//    suspend fun getRoom(login: String, password: String): User?

    @Query("SELECT * FROM Rooms WHERE project_id=:projectId")
    suspend fun getRoomsByProject(projectId:Int): List<Room>?

    @Insert
    suspend fun insertRoom(room: Room)
}