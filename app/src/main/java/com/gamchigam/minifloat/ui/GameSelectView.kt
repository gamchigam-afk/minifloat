package com.gamchigam.minifloat.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.gamchigam.minifloat.game.GameManager
import com.gamchigam.minifloat.game.MiniGame

class GameSelectView(
    context: Context,
    private val gameManager: GameManager,
    private val onGameSelected: (MiniGame) -> Unit,
    private val onClose: () -> Unit
) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(20.dp(), 20.dp(), 20.dp(), 20.dp())

        background = GradientDrawable().apply {
            cornerRadius = 28.dp().toFloat()
            setColor(Color.rgb(21, 27, 35))
        }

        val title = TextView(context).apply {
            text = "게임 선택"
            textSize = 24f
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

        gameManager.getGames().forEach { game ->
            addGameButton(game)
        }

        val closeButton = Button(context).apply {
            text = "닫기"
            setOnClickListener {
                onClose()
            }
        }

        addView(
            closeButton,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                48.dp()
            )
        )
    }

    private fun addGameButton(game: MiniGame) {
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

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
