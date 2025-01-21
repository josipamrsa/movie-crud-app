package com.jmrsa.moviecrudapp.data.db.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jmrsa.moviecrudapp.domain.model.Cast
import com.jmrsa.moviecrudapp.domain.model.Director

class DataConverter {
    @TypeConverter
    fun fromDirector(director: Director) =
        Gson().toJson(director)

    @TypeConverter
    fun toDirector(value: String) =
        Gson().fromJson(value, Director::class.java)

    @TypeConverter
    fun fromCastList(castList: List<Cast>) =
        Gson().toJson(castList)

    @TypeConverter
    fun toCastList(value: String) =
        Gson().toJson(value, Array<Cast>::class.java).toList()

}