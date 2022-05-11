package com.dramtar.dogfreinds.presenter.main

/**
 * Created by Dramtar on 09.05.2022
 */
sealed class AddingNewUserFromEvent {
    data class EmailChanged(val email: String) : AddingNewUserFromEvent()
    data class FirstNameChanged(val firstName: String) : AddingNewUserFromEvent()
    data class LastNameChanged(val lastName: String) : AddingNewUserFromEvent()
    data class GenderChanged(val gender: String) : AddingNewUserFromEvent()
    data class DateChanged(val date: Long) : AddingNewUserFromEvent()
    data class AvatarChanged(val avatar: String) : AddingNewUserFromEvent()

    object Adding : AddingNewUserFromEvent()
}