package com.jmrsa.moviecrudapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserWithMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUser(userId: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserFavorites(userId: Int) : Flow<List<UserWithMovies>>

    @Transaction
    @Update
    suspend fun updateUserFavorites(userId: Int, movieId: Int)
}