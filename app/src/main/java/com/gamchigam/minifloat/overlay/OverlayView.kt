package com.gamchigam.minifloat.overlay

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.gamchigam.minifloat.game.GameManager
import com.gamchigam.minifloat.game.MiniGame

class OverlayView(
    context: Context,
    private val onClose: () -> Unit,
    private val onGameSelected: (MiniGame) -> Unit
) : LinearLayout(context) {

    private val gameManager = GameManager()

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(24.dp(), 24.dp(), 24.dp(), 24.dp())

        background = GradientDrawable().apply {
            cornerRadius = 28.dp().toFloat()
            setColor(Color.rgb(21, 27, 35))
        }

        addTitle()
        addGameButtons()
        addCloseButton()
    }

    private fun addTitle() {
        val title = TextView(context).apply {
            text = "MiniFloat"
            textSize = 26f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        addView(
            title,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                55.dp()
            )
        )

        val subtitle = TextView(context).apply {
            text = "미니게임을 선택하세요"
            textSize = 14f
            setTextColor(Color.rgb(170, 180, 195))
            gravity = Gravity.CENTER
        }

        addView(
            subtitle,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                35.dp()
            )
        )
    }

    private fun addGameButtons() {
        gameManager.getGames().forEach { game ->
            val button = Button(context).apply {
                text = game.name
                textSize = 14f

                setOnClickListener {
                    onGameSelected(game)
                }
            }

            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                42.dp()
            ).apply {
                setMargins(0, 3.dp(), 0, 3.dp())
            }

            addView(button, params)
        }
    }

    private fun addCloseButton() {
        val button = Button(context).apply {
            text = "닫기"

            setOnClickListener {
                onClose()
            }
        }

        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            48.dp()
        ).apply {
            topMargin = 10.dp()
        }

        addView(button, params)
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
