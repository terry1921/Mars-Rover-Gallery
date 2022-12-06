package com.mx.rockstar.core.net.di

import com.mx.rockstar.core.net.interceptor.HttpRequestInterceptor
import com.mx.rockstar.core.net.service.RoverClient
import com.mx.rockstar.core.net.service.RoverService
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://mars-photos.herokuapp.com/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRoverService(retrofit: Retrofit): RoverService {
        return retrofit.create(RoverService::class.java)
    }

    @Provides
    @Singleton
    fun provideRoverClient(roverService: RoverService): RoverClient {
        return RoverClient(roverService)
    }

}