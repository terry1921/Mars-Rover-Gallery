package com.mx.rockstar.core.database

import com.mx.rockstar.core.database.entity.mapper.asEntity
import com.mx.rockstar.core.test.MockUtil.mockPhoto
import com.mx.rockstar.core.test.MockUtil.mockPhoto1
import com.mx.rockstar.core.test.MockUtil.mockPhotoList
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
class PhotoDaoTest : LocalDatabase() {

    private lateinit var photoDao: PhotoDao

    @Before
    fun init() {
        photoDao = db.photoDao()
    }

    @Test
    fun insertAndLoadPhotoListTest() = runBlocking {
        val mockDataList = mockPhotoList().asEntity()
        photoDao.insertPhotosList(mockDataList)

        val loadFromDB = photoDao.getPhotoList()
        assertThat(loadFromDB.toString(), `is`(mockDataList.toString()))

        val mockData = listOf(mockPhoto(), mockPhoto1()).asEntity()[0]
        assertThat(loadFromDB[0].toString(), `is`(mockData.toString()))
    }

    @Test
    fun deletePhotoData() = runBlocking {
        val mockDataList = mockPhotoList().asEntity()
        photoDao.insertPhotosList(mockDataList)

        assertThat(photoDao.getPhotoList(), `is`(mockDataList))

        photoDao.deletePhotos()

        assertThat(photoDao.getPhotoList(), `is`(emptyList()))
    }

}