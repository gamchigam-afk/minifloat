package com.gamchigam.minifloat.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainScreen(
    context: Context,
    private val onStart: () -> Unit
) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(32.dp(), 32.dp(), 32.dp(), 32.dp())

        background = GradientDrawable().apply {
            setColor(Color.rgb(11, 15, 20))
        }

        val title = TextView(context).apply {
            text = "MiniFloat"
            textSize = 32f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        addView(
            title,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                80.dp()
            )
        )

        val description = TextView(context).apply {
            text = "다른 앱을 사용하는 중에도\n짧은 미니게임을 즐겨보세요!"
            textSize = 16f
            setTextColor(Color.rgb(170, 180, 195))
            gravity = Gravity.CENTER
        }

        addView(
            description,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                80.dp()
            )
        )

        val startButton = Button(context).apply {
            text = "플로팅 버튼 시작"
            textSize = 16f
            setOnClickListener {
                onStart()
            }
        }

        val buttonParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            56.dp()
        ).apply {
            topMargin = 24.dp()
        }

        addView(startButton, buttonParams)
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
