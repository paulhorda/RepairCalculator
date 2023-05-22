package com.example.repaircalculator.ui.screen.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.CommonStatisticAdapter
import com.example.repaircalculator.adapter.RoomsStatisticsAdapter
import com.example.repaircalculator.data.dao.MaterialDao
import com.example.repaircalculator.data.dao.NoteDao
import com.example.repaircalculator.data.dao.RoomDao
import com.example.repaircalculator.data.dao.StageDao
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.Room
import com.example.repaircalculator.data.entity.Stage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val roomDao: RoomDao,
    private val materialDao: MaterialDao,
    private val noteDao: NoteDao,
    private val stageDao: StageDao,
) : ViewModel() {

    var commonStatisticAdapter = CommonStatisticAdapter()
    var roomsStatisticsAdapter = RoomsStatisticsAdapter()

    private val _liveData = MutableLiveData<List<Stage>>()
    val liveData: LiveData<List<Stage>> = _liveData

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

    suspend fun getMaterials(roomId: Int): List<Material> {
        val notes = noteDao.getNotesOrNull(roomId)!!

        val tablesElements = mutableListOf<Material>()
        notes.forEach {
            materialDao.getMaterialsOrNull(it.id)?.forEach {
                materialDao.getMaterialOrNull(it.id)?.let { it1 -> tablesElements.add(it1) }
            }
        }
        return tablesElements.toList()
    }

    suspend fun getNotes(roomId: Int): List<Note> {
        return noteDao.getNotesOrNull(roomId)!!
    }

    fun getStages(projectId: Int) {
        viewModelScope.launch {
            stageDao.getStagesForProject(projectId).collect {
                _liveData.value = it
            }
        }
    }

    fun getLv(projectId: Int): LiveData<List<Stage>> {

            return stageDao.getStagesForProject(projectId).asLiveData()

    }

    suspend fun getFullPriceFor(projectId: Int) {

    }
}