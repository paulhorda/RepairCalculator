package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.NoteElement
import com.example.repaircalculator.data.entity.Room

@Dao
interface NoteElementDao {

    @Query("SELECT * FROM NoteElements WHERE parent_id=:parentId")
    suspend fun getNoteElements(parentId:Int): List<NoteElement>?
//
    @Insert
    suspend fun insertNoteElement(noteElement: NoteElement)
}