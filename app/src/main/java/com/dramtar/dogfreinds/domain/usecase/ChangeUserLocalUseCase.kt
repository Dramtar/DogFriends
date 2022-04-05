package com.dramtar.dogfreinds.domain.usecase

import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.repository.Repository

/**
 * Created by Dramtar on 15.03.2022
 */

class ChangeUserLocalUseCase(private val repository: Repository) {
    suspend fun execute(user: User): User {
        val newUser = User(
            id = user.id,
            gender = user.gender,
            firstName = "${user.firstName} changed name",
            lastName = user.lastName,
            age = user.age,
            date = user.date,
            pictureLarge = user.pictureLarge,
            pictureMedium = user.pictureMedium,
            dogAvatar = user.dogAvatar,
            email = user.email
        )
        repository.saveUser(newUser)
        return newUser
    }
}