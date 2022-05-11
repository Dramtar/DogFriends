package com.dramtar.dogfreinds.presenter.main

/**
 * Created by Dramtar on 09.05.2022
 */
data class AddingNewUserFromState(
    val email: String = "",
    val emailError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val gender: String = "Gender",
    val genderError: String? = null,
    val date: Long = 0,
    val avatar: String = ""
)