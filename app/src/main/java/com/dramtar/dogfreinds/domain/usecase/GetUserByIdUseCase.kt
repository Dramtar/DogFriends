package com.dramtar.dogfreinds.domain.usecase

import com.dramtar.dogfreinds.domain.Repository

/**
 * Created by Dramtar on 06.05.2022
 */
class GetUserByIdUseCase(private val repository: Repository) {

    suspend fun execute(id: Int) = repository.getUserById(id = id)
}