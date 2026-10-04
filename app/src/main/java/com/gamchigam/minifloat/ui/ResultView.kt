package com.gamchigam.minifloat.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.gamchigam.minifloat.game.GameResult

class ResultView(
    context: Context,
    result: GameResult,
    private val onRetry: () -> Unit,
    private val onClose: () -> Unit
) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(24.dp(), 24.dp(), 24.dp(), 24.dp())

        background = GradientDrawable().apply {
            cornerRadius = 28.dp().toFloat()
            setColor(Color.rgb(21, 27, 35))
        }

        val title = TextView(context).apply {
            text = if (result.isNewBest) {
                "🏆 신기록!"
            } else {
                "게임 종료"
            }

            textSize = 26f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        addView(title, matchParams(60))

        val score = TextView(context).apply {
            text = "점수  ${result.score}"
            textSize = 22f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        addView(score, matchParams(55))

        val coins = TextView(context).apply {
            text = "🪙 +${result.coins} COIN"
            textSize = 17f
            setTextColor(Color.rgb(170, 180, 195))
            gravity = Gravity.CENTER
        }

        addView(coins, matchParams(45))

        val retryButton = Button(context).apply {
            text = "다시 하기"

            setOnClickListener {
                onRetry()
            }
        }

        addView(retryButton, matchParams(50))

        val closeButton = Button(context).apply {
            text = "게임 선택으로"

            setOnClickListener {
                onClose()
            }
        }

        addView(closeButton, matchParams(50))
    }

    private fun matchParams(heightDp: Int): LayoutParams {
        return LayoutParams(
            LayoutParams.MATCH_PARENT,
            heightDp.dp()
        ).apply {
            setMargins(0, 4.dp(), 0, 4.dp())
        }
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
