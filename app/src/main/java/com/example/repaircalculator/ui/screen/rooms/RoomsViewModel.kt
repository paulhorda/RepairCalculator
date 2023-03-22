package com.example.repaircalculator.ui.screen.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.RoomsAdapter
import com.example.repaircalculator.data.dao.RoomDao
import com.example.repaircalculator.data.entity.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomDao: RoomDao
) : ViewModel() {

    val adapter = RoomsAdapter()

    fun setRooms(projectId: Int) {
        viewModelScope.launch {
            adapter.rooms = roomDao.getRoomsByProject(projectId)?: emptyList()
//            roomDao.insertRoom(Room(0,
//                projectId,
//                "Bathroom",
//                3.0,
//                24.0,
//                6.0,
//                System.currentTimeMillis()))
//            roomDao.insertRoom(Room(0,
//                projectId,
//                "Bad Master",
//                3.0,
//                24.0,
//                6.0,
//                System.currentTimeMillis()))
//            roomDao.insertRoom(Room(0,
//                projectId,
//                "Bathroom Master",
//                3.0,
//                24.0,
//                6.0,
//                System.currentTimeMillis()))
//
//            roomDao.insertRoom(Room(0,
//                projectId,
//                "Kitchen",
//                3.0,
//                24.0,
//                6.0,
//                System.currentTimeMillis()))
//            roomDao.insertRoom(Room(0,
//                projectId,
//                "Living Room",
//                3.0,
//                24.0,
//                6.0,
//                System.currentTimeMillis()))
//            roomDao.insertRoom(Room(0,
//                projectId,
//                "Bathroom",
//                3.0,
//                24.0,
//                6.0,
//                System.currentTimeMillis()))

        }
    }

    init {
        viewModelScope.launch {



        }
    }
}