package com.mx.rockstar.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Camera(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "rover_id")
    val roverId: Int,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "full_name")
    val fullName: String? = null

)

enum class CameraAbbrev() {
    FHAZ,
    RHAZ,
    MAST,
    CHAMCAM,
    MAHLI,
    MARDI,
    NAVCAM,
    PANCAM,
    MINITES;

    override fun toString(): String = this.name

}