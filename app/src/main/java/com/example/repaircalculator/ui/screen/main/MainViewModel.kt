package com.example.repaircalculator.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.adapter.ProjectAdapter
import com.example.repaircalculator.data.dao.ProjectDao
import com.example.repaircalculator.data.entity.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*

@HiltViewModel
class MainViewModel @javax.inject.Inject constructor(
    private val projectDao: ProjectDao,
) : ViewModel() {

    var adapter = ProjectAdapter()

    private val currentProjects = mutableListOf<Project>()

    fun observe() {
        viewModelScope.launch {
            projectDao.getProjects().collect() {
                adapter.setProjects(it)
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

            projectDao.insertProject(Project(0,
                "Build 1",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "B 2",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Bagr 12",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Element of",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Build 1",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "B 2",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Bagr 12",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Element of",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Build 1",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "B 2",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Bagr 12",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Element of",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Build 1",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "B 2",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Bagr 12",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Element of",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Build 1",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "B 2",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Bagr 12",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
            projectDao.insertProject(Project(0,
                "Element of",
                System.currentTimeMillis(),
                System.currentTimeMillis() * 2,
                System.currentTimeMillis()))
        }


    }
}