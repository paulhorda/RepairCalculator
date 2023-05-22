package com.example.repaircalculator.ui.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.data.dao.ProjectDao
import com.example.repaircalculator.data.dao.RoomDao
import com.example.repaircalculator.data.entity.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val roomDao: RoomDao,
    private val projectDao: ProjectDao,
) : ViewModel() {

    private val _project = MutableLiveData<Project>()
    val project: LiveData<Project> = _project

    fun updateLiveData(projectId: Int) {
        viewModelScope.launch {
            projectDao.getProjectByIdFlow(projectId).collect {
                _project.postValue(it)
            }
        }
    }

}