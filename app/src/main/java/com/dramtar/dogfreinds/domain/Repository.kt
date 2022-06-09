package com.dramtar.dogfreinds.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.utils.Result
import kotlinx.coroutines.flow.Flow


/**
 * Created by Dramtar on 14.03.2022
 */
interface Repository {
    // User
    @ExperimentalPagingApi
    fun getUsers(): Flow<PagingData<User>>
    suspend fun saveUsers(users: List<User>)
    suspend fun saveUser(user: User)
    suspend fun getUserById(id: Int): Flow<User?>
    suspend fun updateUser(user: User)

    //Dog
    suspend fun getLocalDog(email: String): Result<Dog>
    suspend fun getRemoteDog(email: String): Result<Dog>
    suspend fun saveDog(dog: Dog)

}