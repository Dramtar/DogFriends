package com.dramtar.dogfreinds.domain.usecase

import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.repository.Repository

/**
 * Created by Dramtar on 15.03.2022
 */
class SaveUserLocalUseCase(private val repository: Repository) {
    suspend fun execute(users: List<User>?) {
        repository.saveUsers(users)
    }
}