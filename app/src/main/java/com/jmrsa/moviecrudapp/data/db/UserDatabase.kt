package com.jmrsa.moviecrudapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jmrsa.moviecrudapp.data.db.utils.DataConverter
import com.jmrsa.moviecrudapp.domain.model.User

@Database(
    entities = [User::class],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "user_db"
    }
}