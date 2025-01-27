package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.repository.PreferencesRepository
import com.jmrsa.moviecrudapp.utils.isNull

interface GetCurrentUserUseCase {
    suspend fun isCurrentUserInPreferences() : Boolean
    suspend fun retrieveCurrentUserFromDatabase()
}

class GetCurrentUserUseCaseImpl(private val preferencesRepository: PreferencesRepository) :
    BaseUseCase(), GetCurrentUserUseCase {
    override suspend fun isCurrentUserInPreferences() : Boolean {
        return preferencesRepository.currentUserEmail.isNull().not()
    }

    override suspend fun retrieveCurrentUserFromDatabase() {
        TODO("Not yet implemented")
    }

}




