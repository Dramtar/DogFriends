package com.dramtar.dogfreinds.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dramtar.dogfreinds.data.local.entity.DogEntity

/**
 * Created by Dramtar on 23.03.2022
 */

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(vararg dogEntity: DogEntity)

    @Query("SELECT * FROM dog_table ORDER BY unique_id LIMIT 1")
    suspend fun getDog(): DogEntity?

    @Query("SELECT * FROM dog_table where userEmail = :email")
    suspend fun getDogByUserEmail(email: String): DogEntity?

    @Query("SELECT * FROM dog_table ORDER BY unique_id")
    suspend fun getAllDogs(): List<DogEntity>

    @Query("DELETE FROM dog_table")
    suspend fun deleteAllDogs()
}