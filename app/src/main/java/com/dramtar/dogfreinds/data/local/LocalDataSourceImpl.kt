package com.dramtar.dogfreinds.data.local

import androidx.paging.PagingSource
import com.dramtar.dogfreinds.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Dramtar on 15.03.2022
 */
class LocalDataSourceImpl @Inject constructor(
    private var userDao: UserDao,
    private var dogDao: DogDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {
    override fun getUsers(): PagingSource<Int, UserEntity> {
        return userDao.getAllUsers()
    }

    override suspend fun saveUsers(users: List<UserEntity>) = withContext(ioDispatcher) {
        userDao.insertListUser(users)
    }

    override suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override fun getUserByID(id: Int): Flow<UserEntity?> {
        return userDao.getUserById(id)
    }

    override suspend fun lastUpdateUser(): Long = withContext(ioDispatcher) {
        val user = userDao.getLastUser()
        return@withContext user?.timestamp ?: 0
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user = user)
    }

    override suspend fun getDogByEmail(email: String): DogEntity? {
        return dogDao.getDogByUserEmail(email = email)
    }

    override suspend fun insertDog(dogEntity: DogEntity) {
        dogDao.insertDog(dogEntity)
    }

    override suspend fun getAllDogs(): List<DogEntity> {
        return dogDao.getAllDogs()
    }

    override suspend fun deleteUserByEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: UserEntity) {
        TODO("Not yet implemented")
    }
}