package com.dramtar.dogfreinds.mapper

import com.dramtar.dogfreinds.data.remote.model.UserModel
import com.dramtar.dogfreinds.domain.model.User

/**
 * Created by Dramtar on 14.03.2022
 */

class UserListMapperRemote :
    BaseMapper<List<UserModel>, List<User>> {
    override fun transformToDomain(type: List<UserModel>): List<User> {
        return type.map { userModel ->
            User(
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

    override fun transformToDto(type: List<User>): List<UserModel> {
        TODO("Not yet implemented")
    }
}
