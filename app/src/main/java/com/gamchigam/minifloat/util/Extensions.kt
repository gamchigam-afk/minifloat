package com.gamchigam.minifloat.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

fun Int.dp(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun Float.dp(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.setPrimaryText() {
    setTextColor(Color.WHITE)
}

fun TextView.setSecondaryText() {
    setTextColor(Color.rgb(170, 180, 195))
}

fun View.roundBackground(
    color: Int = Color.rgb(21, 27, 35),
    radiusDp: Float = 20f
) {
    background = GradientDrawable().apply {
        cornerRadius = radiusDp * resources.displayMetrics.density
        setColor(color)
    }
}

fun ViewGroup.clearChildren() {
    removeAllViews()
}
