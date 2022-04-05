package com.dramtar.dogfreinds.data.sharedprefs

/**
 * Created by Dramtar on 29.03.2022
 */
interface SharedPref {
    fun getLastUserPage(): Int
    fun saveLastUserPage(page: Int)
}