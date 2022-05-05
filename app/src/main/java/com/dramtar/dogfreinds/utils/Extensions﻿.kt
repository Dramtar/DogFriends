package com.dramtar.dogfreinds.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dramtar on 06.04.2022
 */
@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun View.animateAlpha(isShow: Boolean) {
    val alpha = if (isShow) 1f else 0f
    this.animate().alpha(alpha).setDuration(300).start()
}

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadCircle(url: String) {
    Glide.with(this)
        .load(url)
        .transform(CircleCrop())
        .into(this)
}

fun Long.toDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val date = Date(this)
    return sdf.format(date)
}