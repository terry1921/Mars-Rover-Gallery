package com.mx.rockstar.core.data.repository

import androidx.annotation.WorkerThread
import com.mx.rockstar.core.database.RoverDao
import com.mx.rockstar.core.database.entity.mapper.asDomain
import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.net.AppDispatcher
import com.mx.rockstar.core.net.Dispatcher
import com.mx.rockstar.core.net.service.RoverClient
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RoverRepositoryImpl @Inject constructor(
    private val roverClient: RoverClient,
    private val roverDao: RoverDao,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : RoverRepository {

    @WorkerThread
    override fun fetchRovers(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        var rovers = roverDao.getRoverList().asDomain()
        if (rovers.isEmpty()) {
            val response = roverClient.fetchRovers()
            response.suspendOnSuccess {
                rovers = data.rovers
                rovers.forEach { rover ->
                    rover.image =
                        "https://mars-photos.herokuapp.com/explore/images/${rover.name}_rover.jpg"
                }
                roverDao.insertRoverList(rovers.asEntity())
                emit(rovers)
            }.onFailure {
                onError(message())
            }
        } else {
            emit(rovers)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(ioDispatcher)
}