package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.repository.PreferencesRepository
import com.jmrsa.moviecrudapp.domain.repository.UserRepository

interface RegisterUserUseCase {
    suspend fun registerUser(name: String, email: String, password: String, imageUri: String): User?
}

class RegisterUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository
) : BaseUseCase(), RegisterUserUseCase {
    override suspend fun registerUser(name: String, email: String, password: String, imageUri: String): User? {
        val isUserRegistered = userRepository.registerUser(name, email, password, imageUri)
        if (isUserRegistered >= -1) {
            preferencesRepository.currentUserName = name
            preferencesRepository.currentUserEmail = email
            return userRepository.getCurrentUser(email)
        } else {
            return null
        }
    }
}