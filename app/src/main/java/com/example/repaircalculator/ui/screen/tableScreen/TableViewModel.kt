package com.example.repaircalculator.ui.screen.tableScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.MaterialAdapter
import com.example.repaircalculator.adapter.NoteElementAdapter
import com.example.repaircalculator.data.dao.MaterialDao
import com.example.repaircalculator.data.dao.NoteDao
import com.example.repaircalculator.data.dao.NoteElementDao
import com.example.repaircalculator.data.dao.RoomDao
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.NoteElement
import com.example.repaircalculator.data.entity.NoteElementType
import com.example.repaircalculator.data.entity.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val noteElementDao: NoteElementDao,
    private val materialDao: MaterialDao,
    private val roomDao: RoomDao,
) : ViewModel() {

    val adapter = NoteElementAdapter()
    val materialsAdapter = MaterialAdapter()

    suspend fun getNoteElements(noteId: Int): List<NoteElement>? {
        return noteElementDao.getNoteElements(noteId)
    }

    suspend fun getNotes(roomId:Int): List<Note>? {
        return noteDao.getNotesOrNull(roomId)
    }

    suspend fun getMaterialsElements(noteId: Int): List<Material>? {
        return materialDao.getMaterialsOrNull(noteId)
    }

    suspend fun getNote(noteId: Int): Note? {
        return noteDao.getNoteById(noteId)
    }

    suspend fun getRooms(projectId: Int): List<Room>? {
        return roomDao.getRoomsByProject(projectId)
    }

    suspend fun getRoom(roomId: Int): Room? {
        return roomDao.getRoom(roomId)
    }

    fun insertElementImg(parentId:Int, res: Uri) {
        viewModelScope.launch {
            noteElementDao.insertNoteElement(NoteElement(0,
                parentId,
                NoteElementType.IMG,
                res.toString(),
                "",
                1,
                System.currentTimeMillis()))
        }
    }

    fun insertElementText(parentId:Int, content:String) {
        viewModelScope.launch {
            noteElementDao.insertNoteElement(NoteElement(0,
                parentId,
                NoteElementType.TEXT,
                "",
                content,
                1,
                System.currentTimeMillis()))
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }

    init {
//        viewModelScope.launch {
//            noteDao.insertNote(Note(0, 1, "slkdfjlskf", 1, System.currentTimeMillis()))
//        }
    }
}