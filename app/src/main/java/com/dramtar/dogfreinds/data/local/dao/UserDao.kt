package com.dramtar.dogfreinds.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dramtar.dogfreinds.data.local.entity.UserEntity

/**
 * Created by Dramtar on 15.03.2022
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListUser(list: List<UserEntity>)

    @Query("SELECT * FROM user_table WHERE unique_id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM user_table ORDER BY unique_id ASC")
    fun getAllUsers(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM user_table where unique_email = :email")
    suspend fun deleteByEmail(email: String)

    @Query("SELECT * FROM user_table ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastUser(): UserEntity?

    @Query("SELECT COUNT(unique_id) FROM user_table")
    suspend fun getCount(): Int
}