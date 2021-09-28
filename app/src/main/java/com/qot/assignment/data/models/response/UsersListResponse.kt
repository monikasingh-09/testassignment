package com.qot.assignment.data.models.response

import com.qot.assignment.data.models.entity.User

data class UsersListResponse(
    val data: List<User>? = null,
    val total: Int = 0,
    val page: Int = 0,
    val limit: Int = 0
)