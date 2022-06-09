package com.dramtar.dogfreinds.data.remote

import com.dramtar.dogfreinds.utils.QUERY_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Dramtar on 14.03.2022
 */

interface UserApiService {

    @GET("api")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = QUERY_PAGE_SIZE,
        @Query("seed") seed: String = "abc"
    ): Response<ResponseUser>


    @GET()
    suspend fun getDogAvatar(
        @Url url: String,
        @Query("results") results: Int = 10
    ): Response<ResponseDogAvatar>
}