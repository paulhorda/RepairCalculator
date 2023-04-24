package com.example.repaircalculator.ui.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.NoteAdapter
import com.example.repaircalculator.data.dao.NoteDao
import com.example.repaircalculator.data.dao.NoteElementDao
import com.example.repaircalculator.data.dao.ProjectDao
import com.example.repaircalculator.data.dao.RoomDao
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.Project
import com.example.repaircalculator.data.entity.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val roomDao: RoomDao,
    private val noteDao: NoteDao,
    private val projectDao: ProjectDao,
    private val noteElementDao: NoteElementDao,
) : ViewModel() {

    val adapter = NoteAdapter()

//    suspend fun getNoteElements(noteId: Int): List<NoteElement>? {
//        return noteElementDao.getNoteElements(noteId)
//    }
//

    fun updateRoomImage(roomId: Int, image:String) {
        viewModelScope.launch {
            val room = roomDao.getRoom(roomId)
            if(room!=null){
                room.image = image
                roomDao.updateRoom(room)
            }
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insertNote(note)
        }
    }

    suspend fun getRoom(id: Int): Room? {
        return roomDao.getRoom(id)
    }

    suspend fun getProject(id: Int): Project? {
        return projectDao.getProjectById(id)
    }


    fun setNotes(projectId: Int) {
        viewModelScope.launch {
            adapter.notes = noteDao.getNotesOrNull(projectId) ?: emptyList()
//            noteDao.insertNote(Note(0, projectId, "Buy smth", 1, System.currentTimeMillis()))
//            noteDao.insertNote(Note(0, projectId, "Buy materials", 1, System.currentTimeMillis()))
        }
    }
//
//    fun insertElement(res: Uri) {
//        viewModelScope.launch {
//            noteElementDao.insertNoteElement(NoteElement(0,
//                1,
//                NoteElementType.IMG,
//                res.toString(),
//                "",
//                1,
//                System.currentTimeMillis()))
//        }
//    }

    init {
//        viewModelScope.launch {
//            noteDao.insertNote(Note(0, 1, "Bathroom", 1, System.currentTimeMillis()))
//            noteDao.insertNote(Note(0, 1, "Bathroom", 1, System.currentTimeMillis()))
//        }
    }
}