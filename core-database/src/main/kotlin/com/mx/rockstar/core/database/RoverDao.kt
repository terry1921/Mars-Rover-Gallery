package com.mx.rockstar.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mx.rockstar.core.database.entity.RoverEntity

@Dao
interface RoverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoverList(roverList: List<RoverEntity>)

    @Query("SELECT * FROM RoverEntity")
    suspend fun getRoverList(): List<RoverEntity>

    @Query("SELECT * FROM RoverEntity WHERE id == :id")
    suspend fun getRoverById(id: Int): RoverEntity

   @Query("DELETE FROM RoverEntity")
   suspend fun deleteRovers()

}