package com.gamchigam.minifloat.overlay

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class OverlayView(
    context: Context,
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
                60.dp()
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
                40.dp()
            )
        )
    }

    private fun addGameButtons() {
        val games = listOf(
            "⚡ Reaction",
            "👆 Tap Rush",
            "🎯 Target",
            "🧠 Memory",
            "⏱ Timing",
            "💨 Dodge",
            "🔢 Number Order",
            "🎲 Random Button"
        )

        games.forEach { gameName ->
            val button = Button(context).apply {
                text = gameName
                textSize = 15f
                setTextColor(Color.WHITE)
                setOnClickListener {
                    // 게임 선택 기능은 GameManager 구현 후 연결
                }
            }

            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                46.dp()
            ).apply {
                setMargins(0, 4.dp(), 0, 4.dp())
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
            topMargin = 12.dp()
        }

        addView(button, params)
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
