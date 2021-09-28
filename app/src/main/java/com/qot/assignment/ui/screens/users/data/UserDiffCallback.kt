package com.qot.assignment.ui.screens.users.data

import androidx.recyclerview.widget.DiffUtil
import com.qot.assignment.data.models.entity.User

class UserDiffCallback :
    DiffUtil.ItemCallback<User>() {

    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem.id == newItem.id
            && oldItem.picture == newItem.picture
            && oldItem.fullName() == newItem.fullName()

    override fun areItemsTheSame(oldItem: User, newItem: User) =
        oldItem.id == newItem.id
}