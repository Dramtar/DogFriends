package com.dramtar.dogfreinds.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.model.User
import kotlin.random.Random

/**
 * Created by Dramtar on 05.04.2022
 */
class Utils {
    fun generateDogName(url: String, email: String): String {
        val startIndex = url.lastIndexOf("breeds").plus(7)
        val endIndex = url.lastIndexOf("/")
        val breed = url.substring(startIndex = startIndex, endIndex = endIndex)

        val userName = email.substring(0, email.indexOf("."))
        return "$breed $userName "
    }

    @ColorInt
    fun invertColor(@ColorInt color: Int): Int {
        val invertedRed = 255 - color.red
        val invertedGreen = 255 - color.green
        val invertedBlue = 255 - color.blue

        return Color.rgb(invertedRed, invertedGreen, invertedBlue)
    }

    fun initDog(url: String, email: String): Dog {
        return Dog(
            id = null,
            userEmail = email,
            dogName = Utils().generateDogName(url = url, email = email),
            dogPic = url
        )
    }
}

fun User.setRndBGColorToUser(): User {
    val rnd = Random(id)
    val color = Color.rgb(rnd.nextInt(), rnd.nextInt(), rnd.nextInt())
    return User(
        id = id,
        gender = gender,
        firstName = firstName,
        lastName = lastName,
        age = age,
        date = date,
        pictureLarge = pictureLarge,
        pictureMedium = pictureMedium,
        email = email,
        bgColor = color,
        nameColor = Utils().invertColor(color)
    )
}

fun User.changeUser(newName: String): User {
    return User(
        id = id,
        gender = gender,
        firstName = "$firstName $newName",
        lastName = lastName,
        age = age,
        date = date,
        pictureLarge = pictureLarge,
        pictureMedium = pictureMedium,
        email = email
    )
}