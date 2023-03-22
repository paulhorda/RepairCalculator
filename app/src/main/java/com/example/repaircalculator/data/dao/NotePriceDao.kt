package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repaircalculator.data.entity.NotePrice

@Dao
interface NotePriceDao {
    @Query("SELECT * FROM NotePrices WHERE note_id=:noteId")
    suspend fun getPricesForNote(noteId:Int): List<NotePrice>?
//
//    @Query("SELECT * FROM Notes WHERE id=:id LIMIT 1")
//    suspend fun getNoteById(id:Int): Note?
//
    @Insert
    suspend fun insertPrice(notePrice: NotePrice)
//
//    @Update
//    suspend fun updateNote(note: Note)
}