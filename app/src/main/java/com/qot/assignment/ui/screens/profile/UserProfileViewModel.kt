package com.qot.assignment.ui.screens.profile

import android.os.Bundle
import com.qot.assignment.data.Repository
import com.qot.assignment.data.models.entity.User
import com.qot.assignment.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    var userId: String = ""

    fun initArguments(bundle: Bundle): Boolean {
        val user = bundle.getParcelable<User>("user") ?: return false
        userId = user.id
        return true
    }

    fun userInfoObserver() = repository.getCachedUserInfo(userId)

    fun fetchUserInfo() =
        onDefaultThread { repository.getUserInfo(userId) }

}