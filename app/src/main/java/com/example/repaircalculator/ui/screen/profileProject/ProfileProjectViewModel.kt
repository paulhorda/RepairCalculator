package com.example.repaircalculator.ui.screen.profileProject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.ProjectAdapter
import com.example.repaircalculator.data.dao.ProjectDao
import com.example.repaircalculator.data.entity.Project
import com.example.repaircalculator.data.entity.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileProjectViewModel @Inject constructor(
    private val projectDao: ProjectDao,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val userId =
        context.getSharedPreferences("user_id", Context.MODE_PRIVATE)?.getInt("user_id", 0) ?: 0

    var adapter = ProjectAdapter()

    private val currentProjects = mutableListOf<Project>()

    fun observe() {
        viewModelScope.launch {
            projectDao.getProjects(userId).collect() {
                adapter.setProjects(it.sortedBy { value -> value.status })
                currentProjects.clear()
                currentProjects.addAll(it)
            }
        }
    }

}