package com.mx.rockstar.core.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.mx.rockstar.core.model.Photo
import com.squareup.moshi.Moshi
import javax.inject.Inject

@ProvidedTypeConverter
class RoverConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromJson(json: String): Photo.Rover? {
        return moshi.adapter(Photo.Rover::class.java).fromJson(json)
    }

    @TypeConverter
    fun fromRover(rover: Photo.Rover): String {
        return moshi.adapter(Photo.Rover::class.java).toJson(rover)
    }

}