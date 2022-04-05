package com.dramtar.dogfreinds.data.sharedprefs

import android.content.Context
import javax.inject.Inject

/**
 * Created by Dramtar on 10.03.2022
 */

private const val SHARED_PREFS_NAME = "prefs"
private const val KEY_PAGE = "page"
private const val DEFAULT_PAGE = 1

class SharedPrefImpl @Inject constructor(context: Context) : SharedPref {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun getLastUserPage(): Int {
        return sharedPreferences.getInt(KEY_PAGE, DEFAULT_PAGE)
    }

    override fun saveLastUserPage(page: Int) {
        sharedPreferences.edit().putInt(KEY_PAGE, page).apply()
    }
}