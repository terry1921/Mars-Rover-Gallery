package com.mx.rockstar.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mx.rockstar.core.model.Camera
import com.mx.rockstar.core.model.Photo

@Entity
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val sol: Int,
    val earthDate: String?,
    val camera: Camera?,
    val rover: Photo.Rover?,
    val imgSrc: String?,
    val page: Int
)
