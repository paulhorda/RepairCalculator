package com.example.repaircalculator.ui.screen.modalStage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.MaterialAdapter
import com.example.repaircalculator.adapter.PriceAdapter
import com.example.repaircalculator.adapter.StageAdapter
import com.example.repaircalculator.data.dao.MaterialDao
import com.example.repaircalculator.data.dao.NoteDao
import com.example.repaircalculator.data.dao.NotePriceDao
import com.example.repaircalculator.data.dao.StageDao
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.NotePrice
import com.example.repaircalculator.data.entity.Stage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModalBottomSheetViewModel @Inject constructor(
    private val materialDao: MaterialDao,
    private val stageDao: StageDao,
) : ViewModel() {

    val adapter = StageAdapter()

//    fun setPricesForNote(noteId: Int) {
//        viewModelScope.launch {
//            adapter.materials = materialDao.getMaterialsOrNull(noteId) ?: emptyList()
//        }
//    }

    fun insertMaterial(noteId: Int, title: String, count:Int , price: Double) {
        viewModelScope.launch {
            materialDao.insertMaterial(Material(0, noteId,0, title, count, price, System.currentTimeMillis()))
        }
    }

    fun insertStage(stage: Stage) {
        viewModelScope.launch {
            stageDao.insertStage(stage)
        }
    }

    fun updateStage(stage: Stage) {
        viewModelScope.launch {
            stageDao.updateStage(stage)
        }
    }

    fun setStages(projectId:Int){
        viewModelScope.launch {
            stageDao.getStagesForProject(projectId).collect {
                adapter.stages = it
            }
        }
    }
}