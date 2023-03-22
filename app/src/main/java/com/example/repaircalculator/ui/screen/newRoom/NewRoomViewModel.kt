package com.example.repaircalculator.ui.screen.newRoom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.NoteAdapter
import com.example.repaircalculator.data.dao.NoteDao
import com.example.repaircalculator.data.dao.NoteElementDao
import com.example.repaircalculator.data.dao.RoomDao
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRoomViewModel @Inject constructor(
    private val roomDao: RoomDao,
) : ViewModel() {

    fun insertRoom(room: Room){
        viewModelScope.launch {
            roomDao.insertRoom(room)
        }
    }
}