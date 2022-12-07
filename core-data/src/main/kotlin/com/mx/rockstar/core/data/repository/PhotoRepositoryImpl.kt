package com.mx.rockstar.core.data.repository

import androidx.annotation.WorkerThread
import com.mx.rockstar.core.database.PhotoDao
import com.mx.rockstar.core.database.entity.mapper.asDomain
import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.model.Camera
import com.mx.rockstar.core.model.Photo
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

    private var size = 0

    @WorkerThread
    override fun fetchPhotos(
        sol: Int,
        rover: Photo.Rover,
        camera: Camera?,
        page: Int,
        onStart: () -> Unit,
        onComplete: (Int) -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        var photos = filterPhotos(photoDao.getPhotosList(sol, page).asDomain(), camera, rover)
        if (photos.isEmpty()) {
            /**
             * fetches a list of [Photo] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#apiresponse-extensions-for-coroutines)
             */
            val response = roverClient.fetchPhotosList(sol, rover.name, camera?.name, page)
            response.suspendOnSuccess {
                photos = data.photos
                photos.forEach { photo -> photo.page = page }
                photoDao.insertPhotosList(photos.asEntity())
                size = photos.size
                emit(filterPhotos(photoDao.getAllPhotosList(sol, page).asDomain(), camera, rover))
            }.onFailure {
                /** [onFailure] handles the all error cases from the API request fails with [message] */
                size = 0
                onError(message())
            }
        } else {
            size = photos.size
            emit(filterPhotos(photoDao.getAllPhotosList(sol, page).asDomain(), camera, rover))
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete(size)
    }.flowOn(ioDispatcher)

    private fun filterPhotos(
        list: List<Photo>,
        camera: Camera?,
        rover: Photo.Rover
    ): List<Photo> {
        var photoList = list
        photoList = photoList.filter { it.rover?.id == rover.id }
        if (camera != null) photoList = photoList.filter { it.camera?.id == camera.id }
        return photoList
    }

}