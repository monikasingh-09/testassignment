package com.qot.assignment.data.remote

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val api: ApiService) {

    suspend fun getUsers() = api.getUsers(100)

    suspend fun getUser(userId: String) = api.getUser(userId)

}