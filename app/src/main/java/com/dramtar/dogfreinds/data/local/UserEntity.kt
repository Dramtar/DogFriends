package com.dramtar.dogfreinds.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Dramtar on 15.03.2022
 */

@Entity(tableName = "user_table")
data class UserEntity(
    @ColumnInfo(name = "unique_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val gender: String,
    val firstName: String,
    val lastName: String,
    val pictureLarge: String,
    val pictureMedium: String,
    val age: Int,
    val date: Long,
    @ColumnInfo(name = "unique_email")
    val email: String,
    val isVip: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)