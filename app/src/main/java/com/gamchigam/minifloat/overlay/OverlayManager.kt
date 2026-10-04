package com.gamchigam.minifloat.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class OverlayManager(
    private val context: Context
) {

    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var floatingButton: View? = null
    private var overlayView: OverlayView? = null

    fun showFloatingButton() {
        if (floatingButton != null) return

        val button = TextView(context).apply {
            text = "🎮"
            textSize = 24f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)

            background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(Color.rgb(108, 99, 255))
            }

            setOnClickListener {
                showGameMenu()
            }
        }

        val params = WindowManager.LayoutParams(
            64.dp(),
            64.dp(),
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 20.dp()
            y = 180.dp()
        }

        windowManager.addView(button, params)
        floatingButton = button
    }

    private fun showGameMenu() {
        if (overlayView != null) return

        val view = OverlayView(context) {
            removeGameMenu()
        }

        val params = WindowManager.LayoutParams(
            320.dp(),
            480.dp(),
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        windowManager.addView(view, params)
        overlayView = view
    }

    private fun removeGameMenu() {
        overlayView?.let {
            windowManager.removeView(it)
        }

        overlayView = null
    }

    fun removeOverlay() {
        removeGameMenu()

        floatingButton?.let {
            windowManager.removeView(it)
        }

        floatingButton = null
    }

    private fun Int.dp(): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}
