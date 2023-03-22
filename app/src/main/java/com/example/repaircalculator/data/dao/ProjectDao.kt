package com.example.repaircalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repaircalculator.data.entity.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Query("SELECT * FROM Projects WHERE user_id=:userId")
    fun getProjects(userId:Int): Flow<List<Project>>

    @Query("SELECT * FROM Projects WHERE id=:projectId LIMIT 1")
    fun getProjectById(projectId:Int): Project?

    @Insert
    suspend fun insertProject(project: Project)
}