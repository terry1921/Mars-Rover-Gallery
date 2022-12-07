package com.mx.rockstar.marsroversgallery

import app.cash.turbine.test
import com.mx.rockstar.core.data.repository.PhotoRepository
import com.mx.rockstar.core.data.repository.PhotoRepositoryImpl
import com.mx.rockstar.core.database.PhotoDao
import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.model.mapper.asCapsule
import com.mx.rockstar.core.net.service.RoverClient
import com.mx.rockstar.core.net.service.RoverService
import com.mx.rockstar.core.test.MainCoroutinesRule
import com.mx.rockstar.core.test.MockUtil
import com.mx.rockstar.core.test.MockUtil.mockCamera
import com.mx.rockstar.core.test.MockUtil.mockRover
import com.mx.rockstar.marsroversgallery.ui.detail.RoverPhotoViewModel
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class RoverPhotoViewModelTest {

    private lateinit var viewModel: RoverPhotoViewModel
    private lateinit var photoRepository: PhotoRepository
    private val roverService: RoverService = mock()
    private val roverClient: RoverClient = RoverClient(roverService)
    private val photoDao: PhotoDao = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        photoRepository = PhotoRepositoryImpl(roverClient, photoDao, coroutinesRule.testDispatcher)
        viewModel = RoverPhotoViewModel(photoRepository, MockUtil.mockRover())
    }

    @Test
    fun fetchPhotoListTest() = runTest {
        val mockData = MockUtil.mockPhotoList()
        whenever(photoDao.getPhotosList(10, 1)).thenReturn(mockData.asEntity())
        whenever(photoDao.getAllPhotosList(10, 1)).thenReturn(mockData.asEntity())

        photoRepository.fetchPhotos(
            sol = 10,
            rover = mockRover().asCapsule(),
            camera = mockCamera(),
            page = 1,
            onStart = {},
            onComplete = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val item = awaitItem()
            Assert.assertEquals(item.size, 2)
            Assert.assertEquals(item[0].id, 119056)
            Assert.assertEquals(item[0].earthDate, "2004-02-04")
            awaitComplete()
        }

        viewModel.fetchNextPhotoList()

        verify(photoDao, atLeastOnce()).getPhotosList(10, 1)
        verify(photoDao, atLeastOnce()).getAllPhotosList(10, 1)
    }

}