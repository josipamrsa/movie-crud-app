package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserWithMovies
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

interface GetUserFavoritesUseCase {
    suspend fun getUserFavorites(email: String): Flow<List<UserWithMovies>>
}

class GetUserFavoritesUseCaseImpl(
    private val userRepository: UserRepository
) : BaseUseCase(), GetUserFavoritesUseCase {
    override suspend fun getUserFavorites(email: String): Flow<List<UserWithMovies>> {
        return userRepository.getUserFavoriteMovies(email)
    }
}