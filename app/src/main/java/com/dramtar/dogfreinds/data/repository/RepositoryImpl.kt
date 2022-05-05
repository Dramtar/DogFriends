package com.dramtar.dogfreinds.data.repository

import androidx.paging.*
import com.dramtar.dogfreinds.data.local.LocalDataSource
import com.dramtar.dogfreinds.data.local.entity.UserEntity
import com.dramtar.dogfreinds.data.pagingsources.UserRemoteMediator
import com.dramtar.dogfreinds.data.remote.RemoteDataSource
import com.dramtar.dogfreinds.data.sharedprefs.SharedPref
import com.dramtar.dogfreinds.di.scope.IoDispatcher
import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.repository.Repository
import com.dramtar.dogfreinds.utils.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dramtar on 14.03.2022
 */
@Singleton
class RepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val remoteDataSource: RemoteDataSource,
    private val sharedPref: SharedPref,
    private val localDataSource: LocalDataSource
) : Repository {

    @ExperimentalPagingApi
    override fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = QUERY_PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = UserRemoteMediator(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                sharedPref
            ),
            pagingSourceFactory = {
                localDataSource.getUsers()
            }
        ).flow.map { userEntity: PagingData<UserEntity> ->
            userEntity.map { user: UserEntity ->
                user.mapToDomain()
            }
        }
    }

    override suspend fun saveUsers(users: List<User>) {
        users.map { localDataSource.saveUser(user = it.mapToLocal()) }
    }

    override suspend fun saveUser(user: User) {
        localDataSource.saveUser(user = user.mapToLocal())
    }

    override suspend fun getLocalDog(email: String): Result<Dog> = withContext(ioDispatcher) {
        localDataSource.getDogByEmail(email = email)?.let { dogEntity ->
            return@withContext Result.Success(dogEntity.mapToDomain())
        } ?: run {
            return@withContext Result.Success(null)
        }
    }

    override suspend fun getRemoteDog(email: String): Result<Dog> {
        return when (val response = remoteDataSource.getDogAvatar()) {
            is Result.Success -> {
                response.data?.let { data ->
                    val dog = Utils().initDog(url = data.message, email = email)
                    saveDog(dog = dog)
                    Result.Success(dog)
                } ?: run {
                    Result.Success(null)
                }
            }

            is Result.Error -> {
                Result.Error(response.exception)
            }
            else -> Result.Loading
        }
    }

    override suspend fun saveDog(dog: Dog) {
        localDataSource.insertDog(dog.mapToLocal())
    }
}