package com.dramtar.dogfreinds.domain.usecase

import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.repository.Repository
import com.dramtar.dogfreinds.utils.Result

/**
 * Created by Dramtar on 14.03.2022
 */

class GetLocalDogUseCase(private val repository: Repository) {
    suspend fun execute(email: String): Result<Dog> {
        return repository.getLocalDog(email = email)
    }
}