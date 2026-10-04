package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.GameResult
import kotlin.math.abs

class TimingGame : MiniGame {

    override val id = "timing"
    override val name = "⏱ Timing"
    override val description = "정확한 순간에 버튼을 눌러보세요."

    private var targetTime = 0L
    private var started = false
    private var score = 0

    companion object {
        const val TARGET_DELAY_MS = 2000L
        const val PERFECT_RANGE_MS = 50L
    }

    override fun start() {
        targetTime = System.currentTimeMillis() + TARGET_DELAY_MS
        score = 0
        started = true
    }

    fun press(): Int {
        if (!started) return 0

        val now = System.currentTimeMillis()
        val difference = abs(now - targetTime)

        score = when {
            difference <= PERFECT_RANGE_MS -> 1000
            difference <= 100 -> 800
            difference <= 200 -> 600
            difference <= 350 -> 400
            difference <= 500 -> 200
            else -> 50
        }

        started = false

        return score
    }

    fun getTargetTime(): Long {
        return targetTime
    }

    fun isStarted(): Boolean {
        return started
    }

    override fun reset() {
        targetTime = 0L
        started = false
        score = 0
    }

    override fun finish(): GameResult {
        val coins = (score / 100).coerceAtLeast(1)

        return GameResult(
            gameId = id,
            score = score,
            coins = coins
        )
    }
}
