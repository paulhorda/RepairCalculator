package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.Room

@Dao
interface NoteDao {
    @Query("SELECT * FROM Notes WHERE room_id=:roomId")
    suspend fun getNotesOrNull(roomId:Int): List<Note>?

    @Query("SELECT * FROM Notes WHERE id=:id LIMIT 1")
    suspend fun getNoteById(id:Int): Note?

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}