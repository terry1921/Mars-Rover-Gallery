package com.mx.rockstar.core.data.repository

import androidx.annotation.WorkerThread
import com.mx.rockstar.core.model.Rover
import kotlinx.coroutines.flow.Flow

interface RoverRepository {

    @WorkerThread
    fun fetchRovers(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Rover>>

}