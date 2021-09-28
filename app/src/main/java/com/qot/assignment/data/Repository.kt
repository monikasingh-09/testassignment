package com.qot.assignment.data

import com.qot.assignment.data.local.UserDao
import com.qot.assignment.data.models.entity.User
import com.qot.assignment.data.remote.ApiDataSource
import com.qot.assignment.data.remote.ApiResult
import javax.inject.Inject

class Repository @Inject constructor(
    private val userDao: UserDao,
    private val apiDataSource: ApiDataSource
) {

    private fun addUserToCache(user: User) =
        userDao.addUser(user)

    private fun addUsersToCache(users: List<User>) {
        userDao.deleteAll()
        userDao.addUser(*users.toTypedArray())
    }

    private fun getUsersCount() = userDao.getUsersCount()

    fun getUsersFromCache() = userDao.getAllUsers()

    fun getCachedUserInfo(userId: String) = userDao.getUser(userId)


    suspend fun getUsersList(): String {
        when (val response = ApiResult.callApi { apiDataSource.getUsers() }) {
            is ApiResult.Success ->
                addUsersToCache(response.data.data ?: emptyList())
            is Error -> {
                if (getUsersCount() == 0) {
                    return response.message ?: ""
                }
            }
        }
        return ""
    }

    suspend fun getUserInfo(userId: String) {
        val response = ApiResult.callApi { apiDataSource.getUser(userId) }
        (response as? ApiResult.Success)?.data?.let { addUserToCache(it) }
    }

}