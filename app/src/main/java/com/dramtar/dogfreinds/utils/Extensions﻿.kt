package com.dramtar.dogfreinds.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

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