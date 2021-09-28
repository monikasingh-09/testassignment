package com.qot.assignment.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.qot.assignment.data.models.entity.UserAddress

class AddressDbTypeConverter {

    @TypeConverter
    fun toUserAddress(param: String?): UserAddress? {
        return try {
            if (param.isNullOrBlank()) {
                null
            } else {
                Gson().fromJson(param, UserAddress::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }

    @TypeConverter
    fun fromUserAddress(param: UserAddress?): String = try {
        if (param == null)
            ""
        else
            Gson().toJson(param)
    } catch (e: java.lang.Exception) {
        ""
    }
}