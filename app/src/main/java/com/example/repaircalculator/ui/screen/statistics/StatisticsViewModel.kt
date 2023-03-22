package com.example.repaircalculator.ui.screen.statistics

import androidx.lifecycle.ViewModel
import com.example.repaircalculator.adapter.CommonStatisticAdapter
import com.example.repaircalculator.data.dao.NoteDao
import com.example.repaircalculator.data.dao.ProjectDao
import com.example.repaircalculator.data.dao.RoomDao
import com.example.repaircalculator.data.entity.Project
import com.example.repaircalculator.data.entity.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val roomDao: RoomDao,
    private val projectDao: ProjectDao,
    private val noteDao: NoteDao,
) : ViewModel() {

    var commonStatisticAdapter = CommonStatisticAdapter()

    suspend fun getRooms(projectId: Int): List<Room> {
        return roomDao.getRoomsByProject(projectId) ?: emptyList()
    }

    suspend fun getNotesForProject(projectId: Int): Int {
        val rooms = getRooms(projectId)
        var noteNum = 0

        rooms.forEach {
            noteNum += noteDao.getNotesOrNull(it.id)?.count() ?: 0
        }

        return noteNum
    }

    suspend fun getFullPriceFor(projectId: Int) {

    }
//
//    suspend fun getDateProgress(projectId: Int): Long {
////        val project = projectDao.getProjectById(projectId)!!
//        var result:Project
//        projectDao.getProjects(1).collect(){
//                result = it.first { project -> project.id == projectId }
//            }
//        val project = result
//        return (project.dateEnd - project.dateStart - project.dateEnd - System.currentTimeMillis()) /
//                (project.dateEnd - project.dateStart)
////        return 0
//    }


}