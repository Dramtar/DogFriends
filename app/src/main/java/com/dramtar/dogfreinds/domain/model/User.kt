package com.dramtar.dogfreinds.domain.model

import android.graphics.Color

/**
 * Created by Dramtar on 15.03.2022
 */

data class User(
    val id: Int,
    val gender: String,
    val firstName: String,
    val lastName: String,
    val pictureLarge: String,
    val pictureMedium: String,
    val date: String,
    val dogAvatar: String,
    val bgColor: Int = Color.TRANSPARENT,
    val nameColor: Int = Color.GRAY,
    val email: String,
    val age: Int
)