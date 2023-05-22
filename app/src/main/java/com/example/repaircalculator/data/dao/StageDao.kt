package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.repaircalculator.data.entity.NotePrice
import com.example.repaircalculator.data.entity.Stage
import kotlinx.coroutines.flow.Flow

@Dao
interface StageDao {
    @Query("SELECT * FROM Stages WHERE project_id=:projectId")
    fun getStagesForProject(projectId:Int): Flow<List<Stage>>

    @Insert
    suspend fun insertStage(stage: Stage)

    @Update
    suspend fun updateStage(stage: Stage)
}