package com.jmrsa.moviecrudapp.data.repository

import com.jmrsa.moviecrudapp.data.db.UserDao
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserMovieCrossRef
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserWithMovies
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun registerUser(name: String, email: String, password: String, imageUri: String): Long {
        return userDao.insertUser(
            User(
                userName = name,
                email = email,
                password = password,
                imageUri = imageUri
            )
        )
    }

    override suspend fun getCurrentUser(email: String): User {
        return userDao.getUser(email)
    }

    override suspend fun getUserFavoriteMovies(email: String): Flow<List<UserWithMovies>> {
        return userDao.getUserFavorites(email)
    }

    override suspend fun updateUserFavoriteMovies(userId: Int, newMovie: Movie, movieFavorites: List<Movie>) {
        userDao.insertFavoritedMovie(newMovie)

        val oldFavorites = userDao.getCrossRefsForUser(userId)
        userDao.deleteUserMovieCrossRefs(oldFavorites)

        val newCrossRefs = movieFavorites.map { movie ->
            UserMovieCrossRef(userId = userId, id = movie.id)
        }

        userDao.insertUserMovieCrossRefs(newCrossRefs)
    }
}