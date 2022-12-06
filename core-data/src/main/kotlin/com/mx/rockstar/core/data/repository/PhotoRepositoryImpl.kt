package com.mx.rockstar.core.data.repository

import androidx.annotation.WorkerThread
import com.mx.rockstar.core.database.PhotoDao
import com.mx.rockstar.core.database.entity.mapper.asDomain
import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.model.CameraAbbrev
import com.mx.rockstar.core.model.Photo
import com.mx.rockstar.core.model.RoverType
import com.mx.rockstar.core.net.AppDispatcher
import com.mx.rockstar.core.net.Dispatcher
import com.mx.rockstar.core.net.service.RoverClient
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val roverClient: RoverClient,
    private val photoDao: PhotoDao,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : PhotoRepository {

    @WorkerThread
    override fun fetchPhotos(
        sol: Int,
        rover: RoverType,
        camera: CameraAbbrev,
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        var photos = photoDao.getPhotosBySolAndPage(sol, page).asDomain()
        if (photos.isEmpty()) {
            /**
             * fetches a list of [Photo] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#apiresponse-extensions-for-coroutines)
             */
            val response = roverClient.fetchPhotosList(rover, sol, camera, page)
            response.suspendOnSuccess {
                photos = data.photos
                photos.forEach { photo -> photo.page = page }
                photoDao.insertPhotosList(photos.asEntity())
                emit(photos)
            }.onFailure {
                /** [onFailure] handles the all error cases from the API request fails with [message] */
                onError(message())
            }
        } else {
            emit(photos)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(ioDispatcher)


}