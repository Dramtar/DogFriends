package com.dramtar.dogfreinds.utils

import com.dramtar.dogfreinds.data.local.entity.DogEntity
import com.dramtar.dogfreinds.data.local.entity.UserEntity
import com.dramtar.dogfreinds.data.remote.model.UserModel
import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.model.User

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
        email = email
    )
}

fun UserEntity.mapToLocal(): UserEntity {
    return UserEntity(
        id = id,
        gender = gender,
        firstName = firstName,
        lastName = lastName,
        age = age,
        date = date,
        pictureLarge = pictureLarge,
        pictureMedium = pictureMedium,
        email = email
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
        email = email
    )
}

fun UserModel.mapToLocal(): UserEntity {
    return UserEntity(
        id = uId,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        age = dob.age,
        date = dob.date,
        pictureLarge = picture.large,
        pictureMedium = picture.medium,
        email = email
    )
}

fun UserModel.mapToDomain(): User {
    return User(
        id = uId,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        age = dob.age,
        date = dob.date,
        pictureLarge = picture.large,
        pictureMedium = picture.medium,
        email = email
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