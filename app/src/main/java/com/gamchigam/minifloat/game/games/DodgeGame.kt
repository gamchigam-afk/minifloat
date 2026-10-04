package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.MiniGame
import com.gamchigam.minifloat.game.GameResult

class DodgeGame : MiniGame {

    override val id = "dodge"
    override val name = "💨 Dodge"
    override val description = "장애물을 피하면서 최대한 오래 버티세요."

    private var survivedTime = 0L
    private var started = false
    private var startTime = 0L
    private var hit = false

    override fun start() {
        startTime = System.currentTimeMillis()
        survivedTime = 0L
        started = true
        hit = false
    }

    fun update() {
        if (!started) return

        survivedTime = System.currentTimeMillis() - startTime
    }

    fun onHit() {
        if (!started) return

        update()
        hit = true
        started = false
    }

    fun isRunning(): Boolean {
        return started
    }

    fun getSurvivedTime(): Long {
        update()
        return survivedTime
    }

    fun hasHit(): Boolean {
        return hit
    }

    override fun reset() {
        survivedTime = 0L
        started = false
        startTime = 0L
        hit = false
    }

    override fun finish(): GameResult {
        update()
        started = false

        val score = (survivedTime / 100).toInt()
        val coins = (score / 10).coerceAtLeast(1)

        return GameResult(
            gameId = id,
            score = score,
            coins = coins
        )
    }
}
