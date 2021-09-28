package com.qot.assignment.ui.screens.users

import androidx.lifecycle.MutableLiveData
import com.qot.assignment.data.Repository
import com.qot.assignment.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    val errorMsg = MutableLiveData<String>()

    fun observeUsersList() =
        repository.getUsersFromCache()

    fun getUsersList() {
        onDefaultThread {
            val error = repository.getUsersList()
            errorMsg.postValue(error)
        }
    }

}