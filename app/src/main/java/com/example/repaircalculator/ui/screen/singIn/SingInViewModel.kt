package com.example.repaircalculator.ui.screen.singIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repaircalculator.data.dao.UserDao
import com.example.repaircalculator.data.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(
    private val userDao: UserDao,
) : ViewModel() {

    suspend fun getUserOrNull(login: String, password: String): User? {
        return userDao.getUser(login, password)
    }

    suspend fun insertUser(login: String, password: String) {
        userDao.insertUser(User(0, "name", login, password, System.currentTimeMillis()))
    }

    init {
        viewModelScope.launch {
            userDao.insertUser(User(0, "name", "paul", "pass", System.currentTimeMillis()))
            userDao.insertUser(User(0, "name", "sdf", "pass", System.currentTimeMillis()))
        }
    }
}