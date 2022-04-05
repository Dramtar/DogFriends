package com.dramtar.dogfreinds.domain.usecase

import android.graphics.Color
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.repository.Repository
import com.dramtar.dogfreinds.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

/**
 * Created by Dramtar on 14.03.2022
 */
class GetUserUseCase(
    private val repository: Repository
) {

    // add here some business logic :)
    @ExperimentalPagingApi
    fun execute(): Flow<PagingData<User>> {
        return repository.getUsers().map { pdUser: PagingData<User> ->
            pdUser.map { oldUser: User ->
                val color: Int
                val nameColor: Int
                val rnd = Random(oldUser.id)
                val red = rnd.nextInt()
                val green = rnd.nextInt()
                val blue = rnd.nextInt()

                if (oldUser.id % 2 == 0) {
                    color = Color.rgb(red, green, blue)
                    nameColor = Utils().invertColor(red = red, green = green, blue = blue)
                } else {
                    color = Utils().invertColor(red = red, green = green, blue = blue)
                    nameColor = Color.rgb(red, green, blue)
                }

                User(
                    id = oldUser.id,
                    gender = oldUser.gender,
                    firstName = oldUser.firstName,
                    lastName = oldUser.lastName,
                    age = oldUser.age,
                    date = oldUser.date,
                    pictureLarge = oldUser.pictureLarge,
                    pictureMedium = oldUser.pictureMedium,
                    dogAvatar = oldUser.dogAvatar,
                    email = oldUser.email,
                    bgColor = color,
                    nameColor = nameColor
                )
            }
        }
    }
}