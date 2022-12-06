package com.mx.rockstar.core.data.di

import com.mx.rockstar.core.data.repository.PhotoRepository
import com.mx.rockstar.core.data.repository.PhotoRepositoryImpl
import com.mx.rockstar.core.data.repository.RoverRepository
import com.mx.rockstar.core.data.repository.RoverRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPhotoRepository(
        photoRepository: PhotoRepositoryImpl
    ): PhotoRepository

    @Binds
    fun bindsRoverRepository(
        roverRepository: RoverRepositoryImpl
    ): RoverRepository
}