package com.dramtar.dogfreinds.domain.usecase

import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.Repository

/**
 * Created by Dramtar on 11.05.2022
 */
class UpdateUserUseCase(val repository: Repository) {
    suspend fun execute(user: User) {
        repository.updateUser(user = user)
    }
}