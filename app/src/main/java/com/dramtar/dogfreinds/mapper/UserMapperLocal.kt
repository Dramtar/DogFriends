package com.dramtar.dogfreinds.mapper

import com.dramtar.dogfreinds.data.local.entity.UserEntity
import com.dramtar.dogfreinds.domain.model.User

/**
 * Created by Dramtar on 14.03.2022
 */

class UserMapperLocal :
    BaseMapper<UserEntity, User> {
    override fun transformToDomain(type: UserEntity): User {
        return User(
            id = type.id,
            gender = type.gender,
            firstName = type.firstName,
            lastName = type.lastName,
            age = type.age,
            date = type.date,
            pictureLarge = type.pictureLarge,
            pictureMedium = type.pictureMedium,
            dogAvatar = type.dogAvatar,
            email = type.email
        )
    }

    override fun transformToDto(type: User): UserEntity {
        return UserEntity(
            id = type.id,
            gender = type.gender,
            firstName = type.firstName,
            lastName = type.lastName,
            pictureLarge = type.pictureLarge,
            pictureMedium = type.pictureMedium,
            date = type.date,
            age = type.age,
            dogAvatar = type.dogAvatar,
            email = type.email
        )
    }
}
