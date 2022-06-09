package com.dramtar.dogfreinds.data.local

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

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
    fun getUserById(id: Int): Flow<UserEntity?>

    @Query("SELECT * FROM user_table ORDER BY unique_id ASC")
    fun getAllUsers(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user_table where isVip = 0")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM user_table where unique_email = :email")
    suspend fun deleteByEmail(email: String)

    @Query("SELECT * FROM user_table ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastUser(): UserEntity?

    @Query("SELECT COUNT(unique_id) FROM user_table")
    suspend fun getCount(): Int

    @Update
    suspend fun updateUser(user: UserEntity)
}