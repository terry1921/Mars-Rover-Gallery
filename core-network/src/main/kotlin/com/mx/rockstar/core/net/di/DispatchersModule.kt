package com.mx.rockstar.core.net.di

import com.mx.rockstar.core.net.AppDispatcher
import com.mx.rockstar.core.net.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(AppDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}