package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.GameResult
import kotlin.math.abs

class TargetGame : MiniGame {

    override val id = "target"
    override val name = "🎯 Target"
    override val description = "움직이는 목표를 정확하게 맞히세요."

    private var hits = 0
    private var attempts = 0
    private var started = false

    override fun start() {
        hits = 0
        attempts = 0
        started = true
    }

    /**
     * 목표 중심과 터치 위치의 차이를 전달합니다.
     *
     * @param targetX 목표 X 좌표
     * @param targetY 목표 Y 좌표
     * @param tapX 터치 X 좌표
     * @param tapY 터치 Y 좌표
     */
    fun hit(
        targetX: Float,
        targetY: Float,
        tapX: Float,
        tapY: Float
    ): Boolean {
        if (!started) return false

        attempts++

        val distance = kotlin.math.sqrt(
            (targetX - tapX) * (targetX - tapX) +
            (targetY - tapY) * (targetY - tapY)
        )

        val success = distance <= 80f

        if (success) {
            hits++
        }

        return success
    }

    fun getHits(): Int = hits

    fun getAttempts(): Int = attempts

    override fun reset() {
        hits = 0
        attempts = 0
        started = false
    }

    override fun finish(): GameResult {
        started = false

        val score = hits * 100
        val accuracy = if (attempts > 0) {
            hits.toFloat() / attempts
        } else {
            0f
        }

        val bonus = (accuracy * 100).toInt()
        val finalScore = score + bonus
        val coins = (finalScore / 100).coerceAtLeast(1)

        return GameResult(
            gameId = id,
            score = finalScore,
            coins = coins
        )
    }
}
