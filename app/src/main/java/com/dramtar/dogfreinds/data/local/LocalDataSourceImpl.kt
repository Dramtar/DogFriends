package com.dramtar.dogfreinds.data.local

import androidx.paging.PagingSource
import com.dramtar.dogfreinds.data.local.dao.DogDao
import com.dramtar.dogfreinds.data.local.dao.UserDao
import com.dramtar.dogfreinds.data.local.entity.DogEntity
import com.dramtar.dogfreinds.data.local.entity.UserEntity
import com.dramtar.dogfreinds.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
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

    override suspend fun getUserByID(id: Int): UserEntity? = withContext(ioDispatcher) {
        return@withContext userDao.getUserById(id)
    }

    override suspend fun lastUpdateUser(): Long = withContext(ioDispatcher) {
        val user = userDao.getLastUser()
        return@withContext user?.timestamp ?: 0
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
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