package com.dramtar.dogfreinds.di

import com.dramtar.dogfreinds.data.repository.RepositoryImpl
import com.dramtar.dogfreinds.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Dramtar on 14.03.2022
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}