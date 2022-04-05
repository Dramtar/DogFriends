package com.dramtar.dogfreinds.mapper

import com.dramtar.dogfreinds.data.local.entity.UserEntity
import com.dramtar.dogfreinds.data.remote.model.UserModel

/**
 * Created by Dramtar on 14.03.2022
 */

class UserListMapperRemLoc :
    BaseMapper<List<UserModel>, List<UserEntity>> {
    override fun transformToDomain(type: List<UserModel>): List<UserEntity> {
        return type.map { userModel ->
            UserEntity(
                id = userModel.uId,
                gender = userModel.gender,
                firstName = userModel.name.first,
                lastName = userModel.name.last,
                age = userModel.dob.age,
                date = userModel.dob.date,
                pictureLarge = userModel.picture.large,
                pictureMedium = userModel.picture.medium,
                dogAvatar = "",
                email = userModel.email
            )
        }
    }

    override fun transformToDto(type: List<UserEntity>): List<UserModel> {
        TODO("Not yet implemented")
    }
}
