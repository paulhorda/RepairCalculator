package com.example.repaircalculator.ui.screen.modalPrice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.MaterialAdapter
import com.example.repaircalculator.adapter.PriceAdapter
import com.example.repaircalculator.data.dao.MaterialDao
import com.example.repaircalculator.data.dao.NoteDao
import com.example.repaircalculator.data.dao.NotePriceDao
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.NotePrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModalBottomSheetViewModel @Inject constructor(
    private val notePriceDao: NotePriceDao,
) : ViewModel() {

    val adapter = PriceAdapter()

    fun setPricesForNote(noteId: Int) {
        viewModelScope.launch {
            adapter.prices = notePriceDao.getPricesForNote(noteId) ?: emptyList()
        }
    }

    fun insertPrice(noteId: Int, title: String, price: Int) {
        viewModelScope.launch {
            notePriceDao.insertPrice(NotePrice(0, noteId, title, price.toDouble(), System.currentTimeMillis()))
        }
    }
}