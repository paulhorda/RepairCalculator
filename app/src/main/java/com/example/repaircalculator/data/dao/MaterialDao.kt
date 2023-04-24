package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.Room

@Dao
interface MaterialDao {
    @Query("SELECT * FROM Materials WHERE note_id=:noteId")
    suspend fun getMaterialsOrNull(noteId:Int): List<Material>?

    @Query("SELECT * FROM Materials WHERE id=:materialId LIMIT 1")
    suspend fun getMaterialOrNull(materialId:Int): Material?

//    @Query("SELECT * FROM Materials WHERE id=:id LIMIT 1")
//    suspend fun getMaterialById(id:Int): Note?

    @Insert
    suspend fun insertMaterial(material: Material)

    @Update
    suspend fun updateMaterial(material: Material)
}