package com.dramtar.dogfreinds

import com.dramtar.dogfreinds.domain.Repository
import com.dramtar.dogfreinds.domain.usecase.*
import com.dramtar.dogfreinds.presenter.main.MainViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

/**
 * Created by Dramtar on 08.06.2022
 */
class MainViewModelTest {

    private val repository = mock<Repository>()

    private val mainViewModel = MainViewModel(
        getRemoteDogUseCase = GetRemoteDogUseCase(repository),
        getLocalDogUseCase = GetLocalDogUseCase(repository),
        getUserByIdUseCase = GetUserByIdUseCase(repository),
        getUsersUseCase = GetUserUseCase(repository),
        validations = ValidationUseCases(
            emailValidation = EmailValidationUseCase(),
            nameValidation = NameValidationUseCase(),
            genderValidation = GenderValidationUseCase()
        ),
        saveUserUseCase = SaveUserLocalUseCase(repository)
    )

    @Test
    fun `should return correct age`() {
        val ageInUnix = 758762966000L //17.01.1994
        val countedAge = 28
        assertEquals(countedAge, mainViewModel.countAge(ageInUnix))
    }

}