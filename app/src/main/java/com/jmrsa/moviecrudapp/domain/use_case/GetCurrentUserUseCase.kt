package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.repository.PreferencesRepository
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import com.jmrsa.moviecrudapp.utils.isNull

interface GetCurrentUserUseCase {
    //REVIEW: use cases should only provide a single functionality
    // so each of these methods should be a separate use case class
    // which overrides the invoke method
    // https://developer.android.com/topic/architecture#domain-layer
    // https://developer.android.com/topic/architecture/domain-layer#use-cases-kotlin
    suspend fun currentUserEmail() : String?
    suspend fun isCurrentUserInPreferences() : Boolean
    suspend fun retrieveCurrentUserFromDatabase(email: String) : User
    suspend fun clearPreferences()
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

    override suspend fun clearPreferences() {
        preferencesRepository.clearSharedPreferences()
    }
}




