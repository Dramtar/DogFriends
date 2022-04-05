package com.dramtar.dogfreinds.data.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dramtar.dogfreinds.data.remote.UserApiService
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.mapper.UserListMapperRemote
import javax.inject.Inject

/**
 * Created by Dramtar on 14.03.2022
 */

private const val INITIAL_PAGE = 1

class UserRemotePagingSourceImpl @Inject constructor(
    private val userApiService: UserApiService,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> =
        try {

            val page = params.key ?: INITIAL_PAGE
            val nextPage = page + 1
            val result = userApiService.getUsers(page = page)
            if (result.isSuccessful) {
                val userResponse = result.body()
                val mapper = UserListMapperRemote()
                LoadResult.Page(
                    data = mapper.transformToDomain(type = userResponse!!.users),
                    prevKey = null,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Error(NullPointerException())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }
}