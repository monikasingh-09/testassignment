package com.qot.assignment.ui.screens.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qot.assignment.R
import com.qot.assignment.data.models.entity.User
import com.qot.assignment.databinding.DialogUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailDialog : BottomSheetDialogFragment() {
    lateinit var binding: DialogUserDetailBinding

    val viewModel by viewModels<UserProfileViewModel>()

    companion object {
        fun show(activity: AppCompatActivity, user: User) {
            val frag = UserDetailDialog().apply {
                arguments = Bundle().apply {
                    putParcelable("user", user)
                }
            }
            frag.showNow(activity.supportFragmentManager, "detailFrag")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dlg = super.onCreateDialog(savedInstanceState)
        dlg.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            BottomSheetBehavior.from(bottomSheet).state =
                BottomSheetBehavior.STATE_EXPANDED
        }
        return dlg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_user_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initArguments(requireArguments())

        viewModel.userInfoObserver().observe(this) {
            if (it == null) {
                return@observe
            }
            binding.user = it
        }

        binding.btnBack.setOnClickListener { dismiss() }

        viewModel.fetchUserInfo()
    }

}