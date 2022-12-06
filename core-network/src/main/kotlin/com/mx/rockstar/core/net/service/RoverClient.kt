package com.mx.rockstar.core.net.service

import com.mx.rockstar.core.model.CameraAbbrev
import com.mx.rockstar.core.model.RoverType
import com.mx.rockstar.core.net.model.PhotosResponse
import com.mx.rockstar.core.net.model.RoverResponse
import com.skydoves.sandwich.ApiResponse
import java.util.*
import javax.inject.Inject

class RoverClient @Inject constructor(
    private val roverService: RoverService
) {
    suspend fun fetchPhotosList(
        rover: RoverType,
        sol: Int,
        camera: CameraAbbrev,
        page: Int
    ): ApiResponse<PhotosResponse> =
        roverService.fetchPhotos(
            type = rover.name.lowercase(Locale.getDefault()),
            sol = sol,
            camera = camera.name,
            page = page
        )

    suspend fun fetchRovers(): ApiResponse<RoverResponse> = roverService.fetchRovers()
}