package com.dramtar.dogfreinds.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Dramtar on 23.03.2022
 */

@Entity(
    tableName = "dog_table",
    indices = [Index(value = ["unique_url"], unique = true)]
)

data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "unique_id")
    val id: Int?,
    val userEmail: String,
    val dogName: String,
    @ColumnInfo(name = "unique_url")
    val dogPic: String
)