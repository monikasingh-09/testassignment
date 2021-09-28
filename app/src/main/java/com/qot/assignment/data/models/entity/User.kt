package com.qot.assignment.data.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.qot.assignment.data.local.AddressDbTypeConverter
import com.qot.assignment.utils.formattedDate
import kotlinx.parcelize.Parcelize
import java.util.*

@TypeConverters(AddressDbTypeConverter::class)
@Entity
@Parcelize
data class User(
    @PrimaryKey @ColumnInfo(name = "userId") var id: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "firstName") var firstName: String? = "",
    @ColumnInfo(name = "lastName") var lastName: String? = "",
    @ColumnInfo(name = "picture") var picture: String = "",
    @ColumnInfo(name = "gender") var gender: String? = "",
    @ColumnInfo(name = "email") var email: String? = "",
    @ColumnInfo(name = "dateOfBirth") var dateOfBirth: String? = "",
    @ColumnInfo(name = "phone") var phone: String? = "",
    @ColumnInfo(name = "location") var location: UserAddress? = null,
    @ColumnInfo(name = "registerDate") var registerDate: String? = "",
    @ColumnInfo(name = "updatedDate") var updatedDate: String? = "" // of this format: 2021-06-21T21:02:07.374Z
) : Parcelable {

    fun getMemberSince() = registerDate.formattedDate()

    fun getDoB() = dateOfBirth.formattedDate()

    fun fullName() = "${title.replaceFirstChar { it.titlecase(Locale.ROOT) }} $firstName $lastName"

    fun fullAddress() =
        location?.let { return "${it.street}, ${it.city}, ${it.state}, ${it.country}" } ?: ""
}

