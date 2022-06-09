package com.dramtar.dogfreinds.utils

import com.dramtar.dogfreinds.data.local.DogEntity
import com.dramtar.dogfreinds.data.local.UserEntity
import com.dramtar.dogfreinds.data.remote.UserModel
import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.model.User
import java.time.Instant
import java.util.*

/**
 * Created by Dramtar on 10.04.2022
 */
fun UserEntity.mapToDomain(): User {
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
        lastUpdate = timestamp,
        isVip = isVip
    )
}

fun User.mapToLocal(): UserEntity {
    return UserEntity(
        id = id,
        gender = gender,
        firstName = firstName,
        lastName = lastName,
        age = age,
        date = date,
        pictureLarge = pictureLarge,
        pictureMedium = pictureMedium,
        email = email,
        isVip = isVip
    )
}

fun UserModel.mapToLocal(): UserEntity {
    return UserEntity(
        id = uId,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        age = dob.age,
        date = Date.from(Instant.parse(dob.date)).time,
        pictureLarge = picture.large,
        pictureMedium = picture.medium,
        email = email,
        isVip = false
    )
}

fun UserModel.mapToDomain(): User {
    return User(
        id = uId,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        age = dob.age,
        date = Date.from(Instant.parse(dob.date)).time,
        pictureLarge = picture.large,
        pictureMedium = picture.medium,
        email = email,
        lastUpdate = 0,
        isVip = false
    )
}

fun Dog.mapToLocal(): DogEntity {
    return DogEntity(
        id = id,
        userEmail = userEmail,
        dogPic = dogPic,
        dogName = dogName
    )
}

fun DogEntity.mapToDomain(): Dog {
    return Dog(
        id = id,
        userEmail = userEmail,
        dogPic = dogPic,
        dogName = dogName
    )
}