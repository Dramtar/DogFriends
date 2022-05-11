package com.dramtar.dogfreinds.domain.usecase


/**
 * Created by Dramtar on 10.05.2022
 */
class NameValidationUseCase {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "field can't be blank"
            )
        }
        if (email.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name should be at least 2 symbols length"
            )
        }
        return ValidationResult(successful = true)
    }
}