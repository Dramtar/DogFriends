package com.dramtar.dogfreinds.data.remote

import com.dramtar.dogfreinds.data.remote.model.ResponseDogAvatar
import com.dramtar.dogfreinds.data.remote.model.ResponseUser
import com.dramtar.dogfreinds.utils.Result

/**
 * Created by Dramtar on 21.03.2022
 */
interface RemoteDataSource {
    suspend fun getUsers(page: Int): Result<ResponseUser>
    suspend fun getDogAvatar(): Result<ResponseDogAvatar>
}