package com.mx.rockstar.core.net.service

import com.mx.rockstar.core.net.model.PhotosResponse
import com.mx.rockstar.core.net.model.RoverResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoverService {

    @GET("rovers/{type}/photos")
    suspend fun fetchPhotos(
        @Path("type") type : String,
        @Query("sol") sol: Int,
        @Query("camera") camera: String?,
        @Query("page") page: Int
    ) : ApiResponse<PhotosResponse>

    @GET("rovers")
    suspend fun fetchRovers(): ApiResponse<RoverResponse>

}