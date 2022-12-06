package com.mx.rockstar.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mx.rockstar.core.model.Camera

@Entity
data class RoverEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val landingDate: String,
    val launchDate: String,
    val status: String,
    val maxSol: Int,
    val maxDate: String,
    val totalPhotos: Int,
    val cameras: List<Camera>,
    val image: String
)
