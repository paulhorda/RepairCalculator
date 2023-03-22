package com.example.repaircalculator.ui.screen.main

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
import java.util.*

@HiltViewModel
class MainViewModel @javax.inject.Inject constructor(
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

    fun getDataByQuery(query: String) {
        adapter.setProjects(if (query.trim().isEmpty()) {
            currentProjects
        } else {
            currentProjects.asSequence()
                .filter { it.name.lowercase().startsWith(query.lowercase(Locale.ROOT)) }
                .toList()
        })
    }

    init {
        viewModelScope.launch {
//            projectDao.insertProject(Project(0, userId,
//                "Object A",
//                System.currentTimeMillis(),
//                System.currentTimeMillis() * 2,
//                Status.FINISHED,
//                System.currentTimeMillis()))
//            projectDao.insertProject(Project(0, userId,
//                "Object B",
//                System.currentTimeMillis(),
//                System.currentTimeMillis() * 2,
//                Status.IN_PROGRESS,
//                System.currentTimeMillis()))
//            projectDao.insertProject(Project(0, userId,
//                "Object C",
//                System.currentTimeMillis(),
//                System.currentTimeMillis() * 2,
//                Status.NOT_STARTED,
//                System.currentTimeMillis()))
//            projectDao.insertProject(Project(0, 1,
//                "Build f",
//                System.currentTimeMillis(),
//                System.currentTimeMillis() * 2,
//                Status.FINISHED,
//                System.currentTimeMillis()))
//            projectDao.insertProject(Project(0, 1,
//                "Build ip",
//                System.currentTimeMillis(),
//                System.currentTimeMillis() * 2,
//                Status.IN_PROGRESS,
//                System.currentTimeMillis()))
//            projectDao.insertProject(Project(0, 1,
//                "Build ns",
//                System.currentTimeMillis(),
//                System.currentTimeMillis() * 2,
//                Status.NOT_STARTED,
//                System.currentTimeMillis()))

        }


    }
}