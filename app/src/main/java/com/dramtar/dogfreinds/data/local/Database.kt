package com.dramtar.dogfreinds.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Dramtar on 15.03.2022
 */

@Database(entities = [UserEntity::class, DogEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao

    abstract val dogDao: DogDao
}