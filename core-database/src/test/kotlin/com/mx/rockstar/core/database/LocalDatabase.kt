package com.mx.rockstar.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.squareup.moshi.Moshi
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
abstract class LocalDatabase {

    lateinit var db: RoverDatabase

    @Before
    fun initDB() {
        val moshi = Moshi.Builder().build()
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), RoverDatabase::class.java)
            .allowMainThreadQueries()
            .addTypeConverter(CameraConverter(moshi))
            .addTypeConverter(RoverConverter(moshi))
            .addTypeConverter(CamerasConverter(moshi))
            .build()
    }

    @After
    fun closeDB() {
        db.close()
    }

}
