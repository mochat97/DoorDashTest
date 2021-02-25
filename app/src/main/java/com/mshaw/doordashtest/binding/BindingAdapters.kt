package com.mshaw.doordashtest.binding

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.mshaw.doordashtest.R

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    if (url == null || url.isEmpty()) return
    view.load(url)
}

@BindingAdapter("favorited")
fun setFavorited(view: ImageButton, isFavorited: Boolean) {
    val resource = if (isFavorited) {
        R.drawable.ic_favorite_on
    } else {
        R.drawable.ic_favorite_off
    }

    view.setBackgroundResource(resource)
}