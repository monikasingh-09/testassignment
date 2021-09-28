package com.qot.assignment.ui.screens.base

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    open var defaultDispatcher = Dispatchers.IO

    open fun initArgs(intent: Intent) = true

    fun onDefaultThread(block: suspend () -> Unit) =
        viewModelScope.launch(defaultDispatcher) { block() }

}