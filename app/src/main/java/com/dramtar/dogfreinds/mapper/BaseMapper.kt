package com.dramtar.dogfreinds.mapper

/**
 * Created by Dramtar on 14.03.2022
 */

interface BaseMapper<E, D> {

    fun transformToDomain(type: E): D

    fun transformToDto(type: D): E
}
