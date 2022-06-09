package com.dramtar.dogfreinds.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Dramtar on 15.03.2022
 */

@Parcelize
data class ResponseDogAvatar(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String

) : Parcelable