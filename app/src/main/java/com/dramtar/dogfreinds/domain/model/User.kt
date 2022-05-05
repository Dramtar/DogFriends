package com.dramtar.dogfreinds.domain.model

import android.graphics.Color
import com.dramtar.dogfreinds.utils.toDate

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
    val bgColor: Int = Color.TRANSPARENT,
    val nameColor: Int = Color.GRAY,
    val email: String,
    val age: Int,
    val lastUpdate: Long,
    val lastUpdateText: String = lastUpdate.toDate()
)