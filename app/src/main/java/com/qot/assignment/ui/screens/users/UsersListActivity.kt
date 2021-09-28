package com.qot.assignment.ui.screens.users

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.qot.assignment.R
import com.qot.assignment.data.models.entity.User
import com.qot.assignment.databinding.ActivityUserListBinding
import com.qot.assignment.ui.screens.profile.UserDetailDialog
import com.qot.assignment.ui.screens.users.data.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding

    val viewModel by viewModels<UsersListViewModel>()

    @Inject
    lateinit var usersAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)

        initUsersRecyclerView()

        viewModel.observeUsersList().observe(this) { users ->
            if (users.isNullOrEmpty()) {
                showEmptyState()
            } else {
                showUsersList(users)
            }
        }

        viewModel.errorMsg.observe(this) { msg ->
            if (msg.isNullOrBlank()) {
                return@observe
            }
            showErrorState(msg)
        }

        viewModel.getUsersList()
    }

    private fun initUsersRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = usersAdapter

        usersAdapter.onClickUser = {
            UserDetailDialog.show(this, it)
        }
    }

    private fun showErrorState(error: String) {
        binding.showUsers = false
        binding.textViewInfoMsg.text = error
    }

    private fun showEmptyState() {
        binding.showUsers = false
        binding.textViewInfoMsg.setText(R.string.nothing_to_see_here)
    }

    private fun showUsersList(users: List<User>) {
        binding.showUsers = true
        usersAdapter.submitList(users)
    }

}