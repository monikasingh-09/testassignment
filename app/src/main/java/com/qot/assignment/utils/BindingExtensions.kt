package com.qot.assignment.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("app:imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view).load(url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(view)
}

@BindingAdapter("app:showIfNonNull")
fun showViewIfNonNullText(view: View, inputText: String?) {
    if (inputText.isNullOrBlank()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:showView")
fun showView(view: View, show: Boolean) {
    view.isVisible = show
}