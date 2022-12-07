package com.mx.rockstar.core.net.service

import com.mx.rockstar.core.net.model.PhotosResponse
import com.mx.rockstar.core.net.model.RoverResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class RoverClient @Inject constructor(
    private val roverService: RoverService
) {
    suspend fun fetchPhotosList(
        sol: Int,
        rover: String,
        camera: String?,
        page: Int
    ): ApiResponse<PhotosResponse> =
        roverService.fetchPhotos(
            type = rover,
            sol = sol,
            camera = camera,
            page = page
        )

    suspend fun fetchRovers(): ApiResponse<RoverResponse> = roverService.fetchRovers()
}