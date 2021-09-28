package com.qot.assignment.ui.screens.users.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qot.assignment.R
import com.qot.assignment.data.models.entity.User
import com.qot.assignment.databinding.ItemUserBinding
import javax.inject.Inject

class UserListAdapter @Inject constructor() :
    ListAdapter<User, UserListAdapter.UserViewHolder>(UserDiffCallback()) {

    var onClickUser: ((user: User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding
            ?.apply {
                user = getCurrentItem(holder)
                root.setOnClickListener {
                    onClickUser?.invoke(getCurrentItem(holder))
                }
                executePendingBindings()
            }
    }

    private fun getCurrentItem(holder: UserViewHolder): User = currentList[holder.adapterPosition]

    inner class UserViewHolder(viewBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        val binding get() = DataBindingUtil.getBinding<ItemUserBinding?>(this.itemView)

        init {
            DataBindingUtil.bind<ViewDataBinding>(itemView)
        }
    }
}