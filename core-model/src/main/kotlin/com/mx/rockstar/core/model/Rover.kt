package com.mx.rockstar.core.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Rover(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "landing_date")
    val landingDate: String,

    @field:Json(name = "launch_date")
    val launchDate: String,

    @field:Json(name = "status")
    val status: String,

    @field:Json(name = "max_sol")
    val maxSol: Int,

    @field:Json(name = "max_date")
    val maxDate: String,

    @field:Json(name = "total_photos")
    val totalPhotos: Int,

    @field:Json(name = "cameras")
    val cameras: List<Camera>,

    @field:Json(name = "image")
    var image: String = ""

) : Parcelable

enum class RoverType {
    CURIOSITY,
    OPPORTUNITY,
    SPIRIT,
    PERSEVERANCE;

    override fun toString(): String = this.name

    fun toLower(): String = this.name.lowercase()
}