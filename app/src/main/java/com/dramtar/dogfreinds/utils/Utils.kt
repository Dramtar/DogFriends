package com.dramtar.dogfreinds.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
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

    fun setRndBGColorToUser(@NonNull user: User): User {
        val rnd = Random(user.id)
        val color = Color.rgb(rnd.nextInt(), rnd.nextInt(), rnd.nextInt())
        return User(
            id = user.id,
            gender = user.gender,
            firstName = user.firstName,
            lastName = user.lastName,
            age = user.age,
            date = user.date,
            pictureLarge = user.pictureLarge,
            pictureMedium = user.pictureMedium,
            dogAvatar = user.dogAvatar,
            email = user.email,
            bgColor = color,
            nameColor = invertColor(color)
        )
    }

    fun changeUser(user: User, newName: String): User {
        return User(
            id = user.id,
            gender = user.gender,
            firstName = "${user.firstName} $newName",
            lastName = user.lastName,
            age = user.age,
            date = user.date,
            pictureLarge = user.pictureLarge,
            pictureMedium = user.pictureMedium,
            dogAvatar = user.dogAvatar,
            email = user.email
        )
    }
}