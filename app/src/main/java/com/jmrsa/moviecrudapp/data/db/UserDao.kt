package com.jmrsa.moviecrudapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserMovieCrossRef
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserWithMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUser(email: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritedMovie(movie: Movie)

    @Transaction
    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserFavorites(email: String) : Flow<List<UserWithMovies>>

    @Query("SELECT * FROM UserMovieCrossRef WHERE userId = :userId")
    suspend fun getCrossRefsForUser(userId: Int): List<UserMovieCrossRef>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserMovieCrossRefs(crossRef: List<UserMovieCrossRef>)

    @Delete
    suspend fun deleteUserMovieCrossRefs(crossRef: List<UserMovieCrossRef>)
}