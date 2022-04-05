package com.dramtar.dogfreinds.data.local

import androidx.paging.PagingSource
import com.dramtar.dogfreinds.data.local.entity.DogEntity
import com.dramtar.dogfreinds.data.local.entity.UserEntity

/**
 * Created by Dramtar on 15.03.2022
 */
interface LocalDataSource {

    // User Table

    fun getUsers(): PagingSource<Int, UserEntity>

    suspend fun saveUsers(users: List<UserEntity>)

    suspend fun saveUser(user: UserEntity)

    suspend fun getUserByID(id: Int): UserEntity?

    suspend fun lastUpdateUser(): Long

    suspend fun deleteUserByEmail(email: String)

    suspend fun deleteUser(user: UserEntity)

    suspend fun deleteAllUsers()

    // Dog table

    suspend fun insertDog(dogEntity: DogEntity)

    suspend fun getDogByEmail(email: String): DogEntity?

    suspend fun getAllDogs(): List<DogEntity>
}