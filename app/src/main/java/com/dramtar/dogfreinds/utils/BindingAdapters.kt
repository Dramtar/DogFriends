package com.dramtar.dogfreinds.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

/**
 * Created by Dramtar on 05.05.2022
 */
@BindingAdapter("android:imgUrl")
fun loadImgUrdl(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view)
            .load(url)
            .into(view)
    }
}

@BindingAdapter("android:imgUrlCircle")
fun setImgShapeCircle(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view)
            .load(url)
            .transform(CircleCrop())
            .into(view)
    }
}