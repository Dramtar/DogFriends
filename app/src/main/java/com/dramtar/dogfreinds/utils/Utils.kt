package com.dramtar.dogfreinds.utils

import android.graphics.Color

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

    fun invertColor(red: Int, green: Int, blue: Int): Int {
        val invertedRed = 255 - red
        val invertedGreen = 255 - green
        val invertedBlue = 255 - blue

        return Color.rgb(invertedRed, invertedGreen, invertedBlue)
    }
}