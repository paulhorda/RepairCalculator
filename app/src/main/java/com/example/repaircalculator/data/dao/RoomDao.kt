package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.repaircalculator.data.entity.Room

@Dao
interface RoomDao {
    @Query("SELECT * FROM Rooms WHERE id=:roomId LIMIT 1")
    suspend fun getRoom(roomId:Int): Room?

    @Query("SELECT * FROM Rooms WHERE project_id=:projectId")
    suspend fun getRoomsByProject(projectId:Int): List<Room>?

    @Insert
    suspend fun insertRoom(room: Room)

    @Update
    suspend fun updateRoom(room: Room)
}