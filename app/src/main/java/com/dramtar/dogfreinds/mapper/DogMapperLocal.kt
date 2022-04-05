package com.dramtar.dogfreinds.mapper

import com.dramtar.dogfreinds.data.local.entity.DogEntity
import com.dramtar.dogfreinds.domain.model.Dog

/**
 * Created by Dramtar on 14.03.2022
 */

class DogMapperLocal :
    BaseMapper<DogEntity, Dog> {
    override fun transformToDomain(type: DogEntity): Dog {
        return Dog(
            id = type.id,
            userEmail = type.userEmail,
            dogName = type.dogName,
            dogPic = type.dogPic
        )
    }

    override fun transformToDto(type: Dog): DogEntity {
        return DogEntity(
            id = type.id,
            userEmail = type.userEmail,
            dogName = type.dogName,
            dogPic = type.dogPic
        )
    }
}
