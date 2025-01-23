package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.repository.UserRepository

interface RegisterUserUseCase {
    suspend fun registerUser(name: String, email: String, password: String): User?
}

class RegisterUserUseCaseImpl(private val userRepository: UserRepository) : BaseUseCase(), RegisterUserUseCase {
    override suspend fun registerUser(name: String, email: String, password: String) : User? {
        val isUserRegistered = userRepository.registerUser(name, email, password)
        return if (isUserRegistered > -1) userRepository.getCurrentUser(name) else null
    }
}