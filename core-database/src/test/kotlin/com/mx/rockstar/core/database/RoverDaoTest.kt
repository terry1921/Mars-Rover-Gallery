package com.mx.rockstar.core.database

import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.test.MockUtil.mockRover
import com.mx.rockstar.core.test.MockUtil.mockRovers
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23], manifest = Config.NONE)
class RoverDaoTest : LocalDatabase() {

    private lateinit var roverDao: RoverDao

    @Before
    fun init() {
        roverDao = db.roverDao()
    }

    @Test
    fun insertAndLoadRoverLisTest() = runBlocking {
        val mockDataList = mockRovers().asEntity()
        roverDao.insertRoverList(mockDataList)

        val loadFromDB = roverDao.getRoverList()
        assertThat(loadFromDB.toString(), `is`(mockDataList.toString()))

        val mockData = listOf(mockRover()).asEntity()[0]
        assertThat(loadFromDB[0].toString(), `is`(mockData.toString()))
    }

    @Test
    fun deleteRoverData() = runBlocking {
        val mockDataList = mockRovers().asEntity()
        roverDao.insertRoverList(mockDataList)

        assertThat(roverDao.getRoverList(), `is`(mockDataList))

        roverDao.deleteRovers()

        assertThat(roverDao.getRoverList(), `is`(emptyList()))
    }


}