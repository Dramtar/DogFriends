package com.dramtar.dogfreinds.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.Repository
import com.dramtar.dogfreinds.utils.setRndBGColorToUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Dramtar on 14.03.2022
 */
class GetUserUseCase(
    private val repository: Repository
) {
    @ExperimentalPagingApi
    fun execute(): Flow<PagingData<User>> {
        return repository.getUsers().map { pdUser: PagingData<User> ->
            pdUser.map { user: User ->
                if (user.id % 2 == 0) user.setRndBGColorToUser() else user  //add some business logic :)
            }
        }
    }
}