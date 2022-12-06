package com.mx.rockstar.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "sol")
    val sol: Int,

    @field:Json(name = "earth_date")
    val earthDate: String? = null,

    @field:Json(name = "camera")
    val camera: Camera? = null,

    @field:Json(name = "rover")
    val rover: Rover? = null,

    @field:Json(name = "img_src")
    val imgSrc: String? = null,

    var page: Int = 0

) {

    @JsonClass(generateAdapter = true)
    data class Rover(

        @field:Json(name = "id")
        val id: Int,

        @field:Json(name = "name")
        val name: String? = null,

        @field:Json(name = "launch_date")
        val launchDate: String? = null,

        @field:Json(name = "landing_date")
        val landingDate: String? = null,

        @field:Json(name = "status")
        val status: String? = null
    )

}