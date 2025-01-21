package com.jmrsa.moviecrudapp.domain.model.relations.user_favorites

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "id"])
data class UserMovieCrossRef(
    val userId: Int,
    val id: String
)