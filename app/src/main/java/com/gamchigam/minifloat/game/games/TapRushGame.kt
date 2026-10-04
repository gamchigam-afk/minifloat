package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.GameResult

class TapRushGame : MiniGame {

    override val id = "tap_rush"
    override val name = "👆 Tap Rush"
    override val description = "짧은 시간 동안 최대한 많이 터치하세요."

    private var taps = 0
    private var started = false

    companion object {
        const val GAME_TIME_MS = 10_000L
    }

    override fun start() {
        taps = 0
        started = true
    }

    fun tap(): Int {
        if (!started) return taps

        taps++
        return taps
    }

    fun stop() {
        started = false
    }

    fun getTaps(): Int {
        return taps
    }

    override fun reset() {
        taps = 0
        started = false
    }

    override fun finish(): GameResult {
        started = false

        val score = taps * 10
        val coins = (taps / 2).coerceAtLeast(1)

        return GameResult(
            gameId = id,
            score = score,
            coins = coins
        )
    }
}
