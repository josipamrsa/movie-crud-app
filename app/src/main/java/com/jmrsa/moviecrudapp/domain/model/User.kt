package com.jmrsa.moviecrudapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class User(
    val userName: String,
    val email: String,
    val password: String,
    val imageUri: String? = "",
    @PrimaryKey(autoGenerate = true) val userId: Int? = null
)