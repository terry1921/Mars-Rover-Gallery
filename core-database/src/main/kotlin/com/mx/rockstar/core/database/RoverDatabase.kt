package com.mx.rockstar.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mx.rockstar.core.database.entity.PhotoEntity
import com.mx.rockstar.core.database.entity.RoverEntity

@Database(
    entities = [PhotoEntity::class, RoverEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [CameraConverter::class, RoverConverter::class, CamerasConverter::class])
abstract class RoverDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    abstract fun roverDao(): RoverDao

}