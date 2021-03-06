package com.dramtar.dogfreinds.data.pagingsources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bumptech.glide.load.HttpException
import com.dramtar.dogfreinds.data.local.LocalDataSource
import com.dramtar.dogfreinds.data.local.UserEntity
import com.dramtar.dogfreinds.data.remote.RemoteDataSource
import com.dramtar.dogfreinds.data.remote.UserModel
import com.dramtar.dogfreinds.data.sharedprefs.SharedPref
import com.dramtar.dogfreinds.utils.CACHE_TIME_OUT
import com.dramtar.dogfreinds.utils.Result
import com.dramtar.dogfreinds.utils.mapToLocal
import java.io.IOException

/**
 * Created by Dramtar on 28.03.2022
 */

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val sharedPref: SharedPref
) : RemoteMediator<Int, UserEntity>() {
    private var page: Int = sharedPref.getLastUserPage()

    override suspend fun initialize(): InitializeAction {
        return if (System.currentTimeMillis() - localDataSource.lastUpdateUser() <= CACHE_TIME_OUT) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> page = 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    ++page
                }
            }
            when (val response = remoteDataSource.getUsers(page = page)) {
                is Result.Success -> {
                    response.data?.let { data ->
                        if (loadType == LoadType.REFRESH) clearDB()

                        saveToLocal(data.users)
                    }
                }
                is Result.Error -> {}
                else -> {}
            }
            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun clearDB() {
        localDataSource.deleteAllUsers()
        page = 1
        sharedPref.saveLastUserPage(page = page)
    }

    private suspend fun saveToLocal(list: List<UserModel>) {
        sharedPref.saveLastUserPage(page = page)
        localDataSource.saveUsers(list.map { it.mapToLocal() })
    }
}