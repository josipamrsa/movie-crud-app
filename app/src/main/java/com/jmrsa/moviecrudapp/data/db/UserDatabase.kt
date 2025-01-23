package com.jmrsa.moviecrudapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jmrsa.moviecrudapp.data.db.utils.DataConverter
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.model.relations.user_favorites.UserMovieCrossRef

@TypeConverters(DataConverter::class)
@Database(
    entities = [
        User::class,
        Movie::class,
        UserMovieCrossRef::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "user_db"
    }
}