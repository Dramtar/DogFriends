package com.dramtar.dogfreinds.domain.usecase

import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.repository.Repository
import com.dramtar.dogfreinds.utils.changeUser

/**
 * Created by Dramtar on 15.03.2022
 */
const val DUMMY_NAME = "dummy name"

class ChangeUserLocalUseCase(private val repository: Repository) {
    suspend fun execute(user: User): User {
        val newUser = user.changeUser(newName = DUMMY_NAME)
        repository.saveUser(newUser)
        return newUser
    }
}