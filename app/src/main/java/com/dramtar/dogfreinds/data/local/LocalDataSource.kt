package com.dramtar.dogfreinds.data.local

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Dramtar on 15.03.2022
 */
interface LocalDataSource {

    // User Table

    fun getUsers(): PagingSource<Int, UserEntity>

    suspend fun saveUsers(users: List<UserEntity>)

    suspend fun saveUser(user: UserEntity)

    fun getUserByID(id: Int): Flow<UserEntity?>

    suspend fun lastUpdateUser(): Long

    suspend fun deleteUserByEmail(email: String)

    suspend fun deleteUser(user: UserEntity)

    suspend fun deleteAllUsers()

    suspend fun updateUser(user: UserEntity)

    // Dog table

    suspend fun insertDog(dogEntity: DogEntity)

    suspend fun getDogByEmail(email: String): DogEntity?

    suspend fun getAllDogs(): List<DogEntity>
}