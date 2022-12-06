package com.mx.rockstar.marsroversgallery

import app.cash.turbine.test
import com.mx.rockstar.core.data.repository.RoverRepository
import com.mx.rockstar.core.data.repository.RoverRepositoryImpl
import com.mx.rockstar.core.database.RoverDao
import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.net.service.RoverClient
import com.mx.rockstar.core.net.service.RoverService
import com.mx.rockstar.core.test.MainCoroutinesRule
import com.mx.rockstar.core.test.MockUtil
import com.mx.rockstar.marsroversgallery.ui.main.MainViewModel
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

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var roverRepository: RoverRepository
    private val roverService: RoverService = mock()
    private val roverClient: RoverClient = mock()
    private val roverDao: RoverDao = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        roverRepository = RoverRepositoryImpl(roverClient, roverDao, coroutinesRule.testDispatcher)
        viewModel = MainViewModel(roverRepository)
    }

    @Test
    fun fetchPhotosTest() = runTest {
        val mockData = MockUtil.mockRovers()
        whenever(roverDao.getRoverList()).thenReturn(mockData.asEntity())

        roverRepository.fetchRovers(
            onStart = {},
            onComplete = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val rover = awaitItem()
            Assert.assertEquals(rover.size, 1)
            Assert.assertEquals(rover[0].id, 5)
            Assert.assertEquals(rover[0].name, "Curiosity")
            Assert.assertEquals(rover[0].maxSol, 3671)
            Assert.assertEquals(rover[0].totalPhotos, 614335)
            awaitComplete()
        }

        verify(roverDao, atLeastOnce()).getRoverList()
    }

}