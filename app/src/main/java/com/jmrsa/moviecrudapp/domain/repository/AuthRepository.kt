package com.jmrsa.moviecrudapp.domain.repository

import com.jmrsa.moviecrudapp.domain.model.User

interface AuthRepository {
    suspend fun registerUser(name: String, email: String, password: String)
    suspend fun getCurrentUser() : User
}