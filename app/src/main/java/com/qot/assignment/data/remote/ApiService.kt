package com.qot.assignment.data.remote

import com.qot.assignment.data.models.entity.User
import com.qot.assignment.data.models.response.UsersListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("user")
    suspend fun getUsers(@Query("limit") limit: Int): Response<UsersListResponse>

    @GET("user/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<User>
}