package com.mx.rockstar.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mx.rockstar.core.database.entity.PhotoEntity

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotosList(photoList: List<PhotoEntity>)

    @Query("SELECT * FROM PhotoEntity")
    suspend fun getPhotoList(): List<PhotoEntity>

    @Query("SELECT * FROM PhotoEntity WHERE sol == :sol AND page == :page")
    suspend fun getPhotosBySolAndPage(sol: Int, page: Int): List<PhotoEntity>

    @Query("DELETE FROM PhotoEntity")
    suspend fun deletePhotos()

}