package com.dramtar.dogfreinds.data.remote

import com.dramtar.dogfreinds.data.remote.model.ResponseUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dramtar on 19.03.2022
 */

interface DogApiService {
    @GET()
    //@GET("api/breeds/image/random")
    suspend fun getDogPicture(
        @Query("page") page: Int,
        @Query("results") results: Int = 10
    ): Response<ResponseUser>
}