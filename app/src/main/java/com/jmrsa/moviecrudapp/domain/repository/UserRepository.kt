package com.jmrsa.moviecrudapp.domain.repository

import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserWithMovies
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(name: String, email: String, password: String, imageUri: String) : Long
    suspend fun getCurrentUser(email: String) : User
    suspend fun getUserFavoriteMovies(email: String): Flow<List<UserWithMovies>>
    suspend fun updateUserFavoriteMovies(userId: Int, newMovie: Movie, movieFavorites: List<Movie>)
}