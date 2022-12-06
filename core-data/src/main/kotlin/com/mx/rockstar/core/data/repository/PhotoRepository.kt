package com.mx.rockstar.core.data.repository

import androidx.annotation.WorkerThread
import com.mx.rockstar.core.model.CameraAbbrev
import com.mx.rockstar.core.model.Photo
import com.mx.rockstar.core.model.RoverType
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    @WorkerThread
    fun fetchPhotos(
        sol: Int,
        rover: RoverType,
        camera: CameraAbbrev,
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Photo>>

}