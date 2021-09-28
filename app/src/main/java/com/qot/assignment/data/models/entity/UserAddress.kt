package com.qot.assignment.data.models.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAddress(
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val timezone: String? = null
): Parcelable