package com.jmrsa.moviecrudapp.data.db.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jmrsa.moviecrudapp.domain.model.Director
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.model.User
import com.jmrsa.moviecrudapp.domain.model.helpers.CastList
import com.jmrsa.moviecrudapp.domain.model.helpers.DirectorInfo

class DataConverter {
    @TypeConverter
    fun fromDirector(director: DirectorInfo?): String {
        return Gson().toJson(director)
    }

    @TypeConverter
    fun toDirector(value: String?): DirectorInfo? {
        return if (value.isNullOrEmpty()) null else Gson().fromJson(value, DirectorInfo::class.java)
    }

    @TypeConverter
    fun fromCastList(castList: CastList): String? {
        return Gson().toJson(castList)
    }

    @TypeConverter
    fun toCastList(value: String?): CastList? {
        return if (value == null) null
        else Gson().fromJson(value, CastList::class.java)
    }

    @TypeConverter
    fun fromMovieList(movieList: MutableList<Movie>?): String? {
        return Gson().toJson(movieList)
    }

    @TypeConverter
    fun toMovieList(value: String?): MutableList<Movie>? {
        val type = object : TypeToken<MutableList<Movie>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromUserList(userList: MutableList<Movie>): String? {
        return Gson().toJson(userList)
    }

    @TypeConverter
    fun toUserList(value: String?): MutableList<User> {
        val type = object : TypeToken<MutableList<Movie>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(list: MutableList<String>?): String? {
        return if (list == null) null else Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String?): MutableList<String>? {
        if (value == null) return null
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, type)
    }
}