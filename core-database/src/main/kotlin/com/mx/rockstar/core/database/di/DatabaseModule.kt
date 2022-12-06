package com.mx.rockstar.core.database.di

import android.app.Application
import androidx.room.Room
import com.mx.rockstar.core.database.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
        cameraConverter: CameraConverter,
        roverConverter: RoverConverter,
        camerasConverter: CamerasConverter,
    ): RoverDatabase {
        return Room
            .databaseBuilder(application, RoverDatabase::class.java, "Rover.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(cameraConverter)
            .addTypeConverter(roverConverter)
            .addTypeConverter(camerasConverter)
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(appDatabase: RoverDatabase): PhotoDao {
        return appDatabase.photoDao()
    }

    @Provides
    @Singleton
    fun provideRoverDao(appDatabase: RoverDatabase): RoverDao {
        return appDatabase.roverDao()
    }

    @Provides
    @Singleton
    fun provideCameraConverter(moshi: Moshi): CameraConverter {
        return CameraConverter(moshi)
    }

    @Provides
    @Singleton
    fun provideRoverConverter(moshi: Moshi): RoverConverter {
        return RoverConverter(moshi)
    }

    @Provides
    @Singleton
    fun provideCamerasConverter(moshi: Moshi): CamerasConverter {
        return CamerasConverter(moshi)
    }

}