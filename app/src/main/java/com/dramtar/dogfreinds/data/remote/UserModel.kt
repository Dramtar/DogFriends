package com.dramtar.dogfreinds.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Dramtar on 15.03.2022
 */

@Parcelize
data class UserModel(
    val uId: Int,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("name")
    val name: NameModel,

    @SerializedName("dob")
    val dob: DoBModel,

    @SerializedName("picture")
    val picture: PictureModel,

    @SerializedName("email")
    val email: String
) : Parcelable

@Parcelize
data class DoBModel(
    @SerializedName("date")
    val date: String,

    @SerializedName("age")
    val age: Int
) : Parcelable

@Parcelize
class InfoModel(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: Int
) : Parcelable

@Parcelize
data class NameModel(
    @SerializedName("title")
    val title: String,

    @SerializedName("first")
    val first: String,

    @SerializedName("last")
    val last: String

) : Parcelable

@Parcelize
data class PictureModel(
    @SerializedName("large")
    val large: String,

    @SerializedName("medium")
    val medium: String
) : Parcelable