package com.mx.rockstar.core.data.repository

import androidx.annotation.WorkerThread
import com.mx.rockstar.core.model.Camera
import com.mx.rockstar.core.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    @WorkerThread
    fun fetchPhotos(
        sol: Int,
        rover: Photo.Rover,
        camera: Camera?,
        page: Int,
        onStart: () -> Unit,
        onComplete: (Int) -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Photo>>

}