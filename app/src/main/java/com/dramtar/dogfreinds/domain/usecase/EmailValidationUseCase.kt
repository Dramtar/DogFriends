package com.dramtar.dogfreinds.domain.usecase

import android.util.Patterns


/**
 * Created by Dramtar on 10.05.2022
 */
class EmailValidationUseCase {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "field can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not valid email"
            )
        }
        return ValidationResult(successful = true)
    }
}