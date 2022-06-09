package com.dramtar.dogfreinds.di

import android.content.Context
import androidx.room.Room
import com.dramtar.dogfreinds.data.local.Database
import com.dramtar.dogfreinds.data.local.DogDao
import com.dramtar.dogfreinds.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Dramtar on 14.03.2022
 */

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "DogFriends.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: Database): UserDao {
        return database.userDao
    }

    @Singleton
    @Provides
    fun provideDogDao(database: Database): DogDao {
        return database.dogDao
    }
}
