package com.jmrsa.moviecrudapp.data.repository

import android.util.Log
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.repository.AuthRepository

class AuthRepositoryImpl() : AuthRepository {
    override suspend fun registerUser(name: String, email: String, password: String) {
        Log.d("AuthRepo", "Registered")
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }
}