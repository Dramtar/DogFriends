package com.dramtar.dogfreinds.di

import com.dramtar.dogfreinds.data.local.LocalDataSource
import com.dramtar.dogfreinds.data.local.LocalDataSourceImpl
import com.dramtar.dogfreinds.data.remote.RemoteDataSource
import com.dramtar.dogfreinds.data.remote.UserRemoteDataSourceImpl
import com.dramtar.dogfreinds.data.sharedprefs.SharedPref
import com.dramtar.dogfreinds.data.sharedprefs.SharedPrefImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Dramtar on 14.03.2022
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourcesModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: UserRemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindSharedPref(sharedPrefImpl: SharedPrefImpl): SharedPref
}
