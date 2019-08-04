package yongju.riiidhw.ui.ext

import android.content.Context
import android.util.DisplayMetrics

fun Context.toDP(value: Int): Int {
    return value * resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
}