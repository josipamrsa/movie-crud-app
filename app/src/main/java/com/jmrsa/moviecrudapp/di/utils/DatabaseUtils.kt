package com.jmrsa.moviecrudapp.di.utils

import android.app.Application
import androidx.room.Room
import com.jmrsa.moviecrudapp.data.db.UserDao
import com.jmrsa.moviecrudapp.data.db.UserDatabase
import com.jmrsa.moviecrudapp.data.repository.UserRepositoryImpl
import com.jmrsa.moviecrudapp.domain.repository.UserRepository

object DatabaseUtils {
   fun provideDatabase(application: Application): UserDatabase =
       Room.databaseBuilder(
           application,
           UserDatabase::class.java,
           UserDatabase.DATABASE_NAME
       ).build()

    fun provideUserDao(database: UserDatabase) : UserDao = database.userDao

    fun provideUserRepository(userDao: UserDao): UserRepository =
        UserRepositoryImpl(userDao)
}