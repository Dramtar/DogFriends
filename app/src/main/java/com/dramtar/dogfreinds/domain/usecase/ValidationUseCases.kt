package com.dramtar.dogfreinds.domain.usecase

/**
 * Created by Dramtar on 10.05.2022
 */
data class ValidationUseCases(
    val emailValidation: EmailValidationUseCase,
    val nameValidation: NameValidationUseCase,
    val genderValidation: GenderValidationUseCase
)