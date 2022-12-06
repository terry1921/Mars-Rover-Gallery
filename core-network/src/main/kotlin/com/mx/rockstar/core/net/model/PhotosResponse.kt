package com.mx.rockstar.core.net.model

import com.mx.rockstar.core.model.Photo
import com.mx.rockstar.core.model.Rover
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosResponse(
    @field:Json(name = "photos") val photos: List<Photo>
)