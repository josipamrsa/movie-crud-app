package com.jmrsa.moviecrudapp.domain.model.relations.user_favorites

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.User

data class UserWithMovies(
    @Embedded val user: User,
    @Relation(
        entity = Movie::class,
        parentColumn = "userId",
        entityColumn = "id",
        associateBy = Junction(UserMovieCrossRef::class)
    )
    val favorites: MutableList<Movie>
)

data class MovieWithUsers(
    @Embedded val movie: Movie,
    @Relation(
        entity = User::class,
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(UserMovieCrossRef::class)
    )
    val favoritedByUsers: MutableList<User>
)

