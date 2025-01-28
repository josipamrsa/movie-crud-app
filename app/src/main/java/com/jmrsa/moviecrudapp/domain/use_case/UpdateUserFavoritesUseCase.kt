package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserWithMovies
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface UpdateUserFavoritesUseCase {
    suspend fun updateUserFavorites(email: String, newMovie: Movie, movieFavorites: List<Movie>)
}

class UpdateUserFavoritesUseCaseImpl(
    private val userRepository: UserRepository
) : BaseUseCase(), UpdateUserFavoritesUseCase {
    override suspend fun updateUserFavorites(email: String, newMovie: Movie, movieFavorites: List<Movie>)  {
        withContext(Dispatchers.IO) {
            val user = userRepository.getCurrentUser(email)
            user.userId?.let { userRepository.updateUserFavoriteMovies(it, newMovie, movieFavorites) }
        }
    }
}