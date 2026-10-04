package com.gamchigam.minifloat.overlay

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import com.gamchigam.minifloat.game.MiniGame

class OverlayManager(
    private val context: Context
) {

    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var floatingButton: TextView? = null
    private var overlayView: OverlayView? = null
    private var gameView: GamePlayView? = null

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
        removeGame()

        if (overlayView != null) return

        val view = OverlayView(
            context = context,
            onClose = {
                removeGameMenu()
            },
            onGameSelected = { game ->
                startGame(game)
            }
        )

        val params = WindowManager.LayoutParams(
            320.dp(),
            560.dp(),
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        windowManager.addView(view, params)
        overlayView = view
    }

    private fun startGame(game: MiniGame) {
        removeGameMenu()

        val view = GamePlayView(
            context = context,
            game = game,
            onFinish = {
                removeGame()
                showGameMenu()
            },
            onBack = {
                removeGame()
                showGameMenu()
            }
        )

        val params = WindowManager.LayoutParams(
            320.dp(),
            520.dp(),
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            0,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        windowManager.addView(view, params)
        gameView = view

        view.startGame()
    }

    private fun removeGameMenu() {
        overlayView?.let {
            runCatching {
                windowManager.removeView(it)
            }
        }

        overlayView = null
    }

    private fun removeGame() {
        gameView?.let {
            runCatching {
                windowManager.removeView(it)
            }
        }

        gameView = null
    }

    fun removeOverlay() {
        removeGame()
        removeGameMenu()

        floatingButton?.let {
            runCatching {
                windowManager.removeView(it)
            }
        }

        floatingButton = null
    }

    private fun Int.dp(): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}
