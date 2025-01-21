package com.jmrsa.moviecrudapp.data.repository

import android.util.Log
import com.jmrsa.moviecrudapp.data.db.UserDao
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun registerUser(name: String, email: String, password: String) {
        Log.d("UserRepo", "Registered")
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }
}