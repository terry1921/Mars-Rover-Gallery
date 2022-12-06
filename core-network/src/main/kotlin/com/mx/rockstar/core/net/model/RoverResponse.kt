package com.mx.rockstar.core.net.model

import com.mx.rockstar.core.model.Rover
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoverResponse(
    @field:Json(name = "rovers") val rovers: List<Rover>
)