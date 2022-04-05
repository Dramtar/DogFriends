package com.dramtar.dogfreinds.data.repository

import android.util.Log
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
import com.dramtar.dogfreinds.mapper.DogMapperLocal
import com.dramtar.dogfreinds.mapper.UserListMapperLocal
import com.dramtar.dogfreinds.mapper.UserMapperLocal
import com.dramtar.dogfreinds.utils.QUERY_PAGE_SIZE
import com.dramtar.dogfreinds.utils.Result
import com.dramtar.dogfreinds.utils.Utils
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
            config = PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                enablePlaceholders = true
            ),
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
                UserMapperLocal().transformToDomain(user)
            }
        }
    }

    override suspend fun saveUsers(users: List<User>?) = withContext(ioDispatcher) {
        if (users != null) {
            UserListMapperLocal().transformToDto(users).let { userEntity ->
                userEntity.forEach { entity ->
                    Log.i("user saved", entity.email)
                    localDataSource.saveUser(user = entity)
                }
            }
        }
    }

    override suspend fun saveUser(user: User) {
        localDataSource.saveUser(user = UserMapperLocal().transformToDto(user))
    }

    override suspend fun getLocalDog(email: String): Result<Dog> = withContext(ioDispatcher) {
        val dogDB = localDataSource.getDogByEmail(email = email)
        if (dogDB != null) {
            val dogDomain = DogMapperLocal().transformToDomain(dogDB)
            return@withContext Result.Success(dogDomain)
        } else {
            return@withContext Result.Success(null)
        }
    }

    override suspend fun getRemoteDog(email: String): Result<Dog> {
        return when (val response = remoteDataSource.getDogAvatar()) {
            is Result.Success -> {
                if (response.data != null) {
                    val url = response.data.message
                    val dog = Dog(
                        id = null,
                        userEmail = email,
                        dogName = Utils().generateDogName(url = url, email = email),
                        dogPic = url
                    )

                    saveDog(dog = dog)

                    Result.Success(dog)
                } else {
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
        localDataSource.insertDog(DogMapperLocal().transformToDto(dog))
    }
}