package com.jmrsa.moviecrudapp.data.repository

import android.util.Log
import com.jmrsa.moviecrudapp.data.db.UserDao
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserWithMovies
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun registerUser(name: String, email: String, password: String): Long {
        return userDao.insertUser(
            User(
                userName = name,
                email = email,
                password = password
            )
        )
    }

    override suspend fun getCurrentUser(email: String): User {
        return userDao.getUser(email)
    }

    override suspend fun getUserFavoriteMovies(userId: Int): Flow<List<UserWithMovies>> {
        return userDao.getUserFavorites(userId)
    }

    override suspend fun updateUserFavoriteMovies(userId: Int, movieId: Int) {
        TODO("Not yet implemented")

        /*

        // Fetch existing cross-references for the user
        val oldFavorites = getCrossRefsForUser(userId)

        // Delete old cross-references
        deleteUserMovieCrossRefs(oldFavorites)

        // Insert new cross-references
        val newCrossRefs = newFavorites.map { movie ->
            UserMovieCrossRef(userId = userId, movieId = movie.id)
        }

        insertUserMovieCrossRefs(newCrossRefs)

        */
    }
}