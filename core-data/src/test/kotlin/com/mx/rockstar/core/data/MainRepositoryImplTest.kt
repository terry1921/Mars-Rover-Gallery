@file:Suppress("SpellCheckingInspection")

package com.mx.rockstar.core.data

import app.cash.turbine.test
import com.mx.rockstar.core.data.repository.PhotoRepository
import com.mx.rockstar.core.data.repository.PhotoRepositoryImpl
import com.mx.rockstar.core.database.PhotoDao
import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.model.CameraAbbrev
import com.mx.rockstar.core.model.RoverType
import com.mx.rockstar.core.net.model.PhotosResponse
import com.mx.rockstar.core.net.service.RoverClient
import com.mx.rockstar.core.net.service.RoverService
import com.mx.rockstar.core.test.MainCoroutinesRule
import com.mx.rockstar.core.test.MockUtil.mockPhotoList
import com.nhaarman.mockitokotlin2.*
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MainRepositoryImplTest {

    private lateinit var repository: PhotoRepository
    private lateinit var client: RoverClient
    private val service: RoverService = mock()
    private val roverDao: PhotoDao = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        client = RoverClient(service)
        repository = PhotoRepositoryImpl(client, roverDao, coroutinesRule.testDispatcher)
    }

    @Test
    fun fetchPhotosFromNetworkTest() = runTest {
        val roverType = RoverType.OPPORTUNITY.toLower()
        val fhaz = CameraAbbrev.FHAZ.name

        val mockData = PhotosResponse(mockPhotoList())
        whenever(roverDao.getPhotosBySolAndPage(sol = SOL_10, page = PAGE_1))
            .thenReturn(emptyList())
        whenever(service.fetchPhotos(type = roverType, sol = SOL_10, camera = fhaz, page = PAGE_1))
            .thenReturn(ApiResponse.of { Response.success(mockData) })

        repository.fetchPhotos(
            sol = SOL_10,
            rover = RoverType.OPPORTUNITY,
            camera = CameraAbbrev.FHAZ,
            page = PAGE_1,
            onStart = {},
            onComplete = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val expectItem = awaitItem()[0]
            assertEquals(expectItem.page, PAGE_1)
            assertEquals(expectItem.sol, SOL_10)
            assertEquals(expectItem.id, 119056)
            assertEquals(expectItem.earthDate, "2004-02-04")
            assertEquals(expectItem, mockPhotoList()[0])
            awaitComplete()
        }

        verify(roverDao, atLeastOnce()).getPhotosBySolAndPage(SOL_10, PAGE_1)
        verify(service, atLeastOnce()).fetchPhotos(
            type = roverType,
            sol = SOL_10,
            camera = fhaz,
            page = PAGE_1
        )
        verify(roverDao, atLeastOnce()).insertPhotosList(mockPhotoList().asEntity())
        verifyNoMoreInteractions(service)
    }

    @Test
    fun fetchPhotosFromDataBaseTest() = runTest {
        val mockData = PhotosResponse(mockPhotoList())
        whenever(roverDao.getPhotosBySolAndPage(sol = SOL_10, page = PAGE_1))
            .thenReturn(mockData.photos.asEntity())

        repository.fetchPhotos(
            sol = SOL_10,
            rover = RoverType.OPPORTUNITY,
            camera = CameraAbbrev.FHAZ,
            page = PAGE_1,
            onStart = {},
            onComplete = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val expectItem = awaitItem()[0]
            assertEquals(expectItem.page, PAGE_1)
            assertEquals(expectItem.sol, SOL_10)
            assertEquals(expectItem.id, 119056)
            assertEquals(expectItem.earthDate, "2004-02-04")
            assertEquals(expectItem, mockPhotoList()[0])
            awaitComplete()
        }

        verify(roverDao, atLeastOnce()).getPhotosBySolAndPage(SOL_10, PAGE_1)
    }

    companion object {
        const val SOL_10: Int = 10
        const val PAGE_1: Int = 1
    }

}