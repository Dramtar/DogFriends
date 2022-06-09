package com.dramtar.dogfreinds.data.remote

import com.dramtar.dogfreinds.di.IoDispatcher
import com.dramtar.dogfreinds.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Dramtar on 21.03.2022
 */
class UserRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userApiService: UserApiService,
) : RemoteDataSource {

    override suspend fun getUsers(page: Int): Result<ResponseUser> = withContext(ioDispatcher) {
        return@withContext try {
            val result = userApiService.getUsers(page = page)
            if (result.isSuccessful) {
                val userResponse = result.body()
                Result.Success(userResponse)
            } else {
                Result.Success(null)
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun getDogAvatar(): Result<ResponseDogAvatar> = withContext(ioDispatcher) {
        return@withContext try {
            val result = userApiService.getDogAvatar("https://dog.ceo/api/breeds/image/random")
            if (result.isSuccessful) {
                val dogAvatarResponse = result.body()
                Result.Success(dogAvatarResponse)
            } else {
                Result.Success(null)
            }
        } catch (exception: java.lang.Exception) {
            Result.Error(exception)
        }
    }
}