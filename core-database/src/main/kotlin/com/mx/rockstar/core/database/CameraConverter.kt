package com.mx.rockstar.core.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.mx.rockstar.core.model.Camera
import com.mx.rockstar.core.model.Photo
import com.squareup.moshi.Moshi
import javax.inject.Inject

@ProvidedTypeConverter
class CameraConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromJson(json: String): Camera? {
        return moshi.adapter(Camera::class.java).fromJson(json)
    }

    @TypeConverter
    fun fromCamera(camera: Camera): String {
        return moshi.adapter(Camera::class.java).toJson(camera)
    }

}