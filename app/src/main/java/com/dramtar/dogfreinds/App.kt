package com.dramtar.dogfreinds

import android.app.Application
import com.dramtar.dogfreinds.data.sharedprefs.SharedPrefImpl
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Dramtar on 14.03.2022
 */

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //clean()
    }

    private fun clean() {
        this.deleteDatabase("DogFriends.db")
        SharedPrefImpl(this).saveLastUserPage(1)
    }
}