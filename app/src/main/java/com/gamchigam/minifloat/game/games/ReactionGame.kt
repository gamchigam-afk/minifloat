package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.GameResult
import com.gamchigam.minifloat.game.MiniGame
import kotlin.random.Random

class ReactionGame : MiniGame {

    override val id = "reaction"
    override val name = "⚡ Reaction"
    override val description = "신호가 나오면 최대한 빠르게 반응하세요."

    private var startTime = 0L
    private var score = 0
    private var started = false

    override fun start() {
        score = 0
        started = true

        // 실제 UI에서는 신호가 표시된 순간 호출
        startTime = System.currentTimeMillis()
    }

    fun react(): Int {
        if (!started) return 0

        val reactionTime =
            System.currentTimeMillis() - startTime

        started = false

        score = when {
            reactionTime < 200 -> 1000
            reactionTime < 300 -> 800
            reactionTime < 400 -> 600
            reactionTime < 500 -> 400
            reactionTime < 700 -> 250
            else -> 100
        }

        return score
    }

    override fun reset() {
        startTime = 0L
        score = 0
        started = false
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
