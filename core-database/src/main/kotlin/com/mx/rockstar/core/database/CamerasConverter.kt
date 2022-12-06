package com.mx.rockstar.core.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.mx.rockstar.core.model.Camera
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class CamerasConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromJson(json: String): List<Camera>? {
        val listCamera = Types.newParameterizedType(List::class.java, Camera::class.java)
        val adapter: JsonAdapter<List<Camera>> = moshi.adapter(listCamera)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun fromCameras(cameras: List<Camera>?): String {
        val listCamera = Types.newParameterizedType(List::class.java, Camera::class.java)
        val adapter: JsonAdapter<List<Camera>> = moshi.adapter(listCamera)
        return adapter.toJson(cameras)
    }

}