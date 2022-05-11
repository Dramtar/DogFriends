package com.dramtar.dogfreinds.domain.usecase

import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.repository.Repository

/**
 * Created by Dramtar on 07.05.2022
 */
class AddUserUseCase {
    suspend operator fun invoke(repository: Repository, user: User) {
        repository.saveUser(user)
    }
}