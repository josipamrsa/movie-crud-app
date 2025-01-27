package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.repository.PreferencesRepository
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import com.jmrsa.moviecrudapp.utils.isNull

interface GetCurrentUserUseCase {
    suspend fun currentUserEmail() : String?
    suspend fun isCurrentUserInPreferences() : Boolean
    suspend fun retrieveCurrentUserFromDatabase(email: String) : User
}

class GetCurrentUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
) :
    BaseUseCase(), GetCurrentUserUseCase {
    override suspend fun currentUserEmail(): String? {
        return preferencesRepository.currentUserEmail
    }

    override suspend fun isCurrentUserInPreferences() : Boolean {
        return preferencesRepository.currentUserEmail.isNull().not()
    }

    override suspend fun retrieveCurrentUserFromDatabase(email: String) : User {
        return userRepository.getCurrentUser(email)
    }

}




