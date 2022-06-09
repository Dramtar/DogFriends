package com.dramtar.dogfreinds.di

import com.dramtar.dogfreinds.domain.Repository
import com.dramtar.dogfreinds.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Dramtar on 14.03.2022
 */

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetUserUseCase(repository: Repository): GetUserUseCase {
        return GetUserUseCase(repository = repository)
    }

    @Provides
    fun provideSaveUserLocalUseCase(repository: Repository): SaveUserLocalUseCase {
        return SaveUserLocalUseCase(repository = repository)
    }

    @Provides
    fun provideChangeUserUseCase(repository: Repository): ChangeUserLocalUseCase {
        return ChangeUserLocalUseCase(repository = repository)
    }

    @Provides
    fun provideGetLocalDogUseCase(repository: Repository): GetLocalDogUseCase {
        return GetLocalDogUseCase(repository = repository)
    }

    @Provides
    fun provideGetRemoteDogUseCase(repository: Repository): GetRemoteDogUseCase {
        return GetRemoteDogUseCase(repository = repository)
    }

    @Provides
    fun provideGetUserByIdUseCase(repository: Repository): GetUserByIdUseCase {
        return GetUserByIdUseCase(repository = repository)
    }

    @Provides
    fun provideValidationsUseCases(): ValidationUseCases {
        return ValidationUseCases(
            nameValidation = NameValidationUseCase(),
            emailValidation = EmailValidationUseCase(),
            genderValidation = GenderValidationUseCase()
        )
    }

    @Provides
    fun provideUpdateUserUseCase(repository: Repository): UpdateUserUseCase {
        return UpdateUserUseCase(repository = repository)
    }
}