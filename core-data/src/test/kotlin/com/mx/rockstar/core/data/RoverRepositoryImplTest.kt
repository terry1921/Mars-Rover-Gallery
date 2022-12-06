package com.mx.rockstar.core.data

import app.cash.turbine.test
import com.mx.rockstar.core.data.repository.RoverRepository
import com.mx.rockstar.core.data.repository.RoverRepositoryImpl
import com.mx.rockstar.core.database.RoverDao
import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.net.model.RoverResponse
import com.mx.rockstar.core.net.service.RoverClient
import com.mx.rockstar.core.net.service.RoverService
import com.mx.rockstar.core.test.MainCoroutinesRule
import com.mx.rockstar.core.test.MockUtil.mockRovers
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

class RoverRepositoryImplTest {

    private lateinit var repository: RoverRepository
    private lateinit var client: RoverClient
    private val service: RoverService = mock()
    private val roverDao: RoverDao = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        client = RoverClient(service)
        repository = RoverRepositoryImpl(client, roverDao, coroutinesRule.testDispatcher)
    }

    @Test
    fun fetchRoversFromNetworkTest() = runTest {
        val mockData = RoverResponse(mockRovers())
        whenever(roverDao.getRoverList()).thenReturn(emptyList())
        whenever(service.fetchRovers()).thenReturn(ApiResponse.of { Response.success(mockData) })

        repository.fetchRovers(
            onStart = {},
            onComplete = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val expectedItem = awaitItem()[0]
            assertEquals(expectedItem.id, 5)
            assertEquals(expectedItem.name, "Curiosity")
            assertEquals(expectedItem.maxSol, 3671)
            assertEquals(expectedItem.totalPhotos, 614335)
            awaitComplete()
        }

        verify(roverDao, atLeastOnce()).getRoverList()
        verify(service, atLeastOnce()).fetchRovers()
        verify(roverDao, atLeastOnce()).insertRoverList(mockRovers().asEntity())
        verifyNoMoreInteractions(service)
    }

    @Test
    fun fetchRoversFromDataBaseTest() = runTest {
        val mockData = RoverResponse(mockRovers())
        whenever(roverDao.getRoverList()).thenReturn(mockData.rovers.asEntity())
        whenever(service.fetchRovers()).thenReturn(ApiResponse.of { Response.success(mockData) })

        repository.fetchRovers(
            onStart = {},
            onComplete = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val expectedItem = awaitItem()[0]
            assertEquals(expectedItem.id, 5)
            assertEquals(expectedItem.name, "Curiosity")
            assertEquals(expectedItem.maxSol, 3671)
            assertEquals(expectedItem.totalPhotos, 614335)
            awaitComplete()
        }

        verify(roverDao, atLeastOnce()).getRoverList()
        verifyNoMoreInteractions(roverDao)
        verifyNoMoreInteractions(service)

    }

}