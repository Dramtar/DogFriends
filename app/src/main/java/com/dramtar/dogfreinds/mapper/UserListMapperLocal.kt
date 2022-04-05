package com.dramtar.dogfreinds.mapper

import com.dramtar.dogfreinds.data.local.entity.UserEntity
import com.dramtar.dogfreinds.domain.model.User

/**
 * Created by Dramtar on 14.03.2022
 */

class UserListMapperLocal :
    BaseMapper<List<UserEntity>, List<User>> {
    override fun transformToDomain(type: List<UserEntity>): List<User> {
        return type.map { userEntity ->
            User(
                id = userEntity.id,
                gender = userEntity.gender,
                firstName = userEntity.firstName,
                lastName = userEntity.lastName,
                age = userEntity.age,
                date = userEntity.date,
                pictureLarge = userEntity.pictureLarge,
                pictureMedium = userEntity.pictureMedium,
                dogAvatar = userEntity.dogAvatar,
                email = userEntity.email
            )
        }
    }

    override fun transformToDto(type: List<User>): List<UserEntity> {
        return type.map { user ->
            UserEntity(
                id = user.id,
                gender = user.gender,
                firstName = user.firstName,
                lastName = user.lastName,
                pictureLarge = user.pictureLarge,
                pictureMedium = user.pictureMedium,
                date = user.date,
                age = user.age,
                dogAvatar = user.dogAvatar,
                email = user.email
            )
        }
    }
}
