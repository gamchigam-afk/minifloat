package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.GameResult
import kotlin.random.Random

class RandomButtonGame : MiniGame {

    override val id = "random_button"
    override val name = "🎲 Random Button"
    override val description = "정답 버튼이 계속 바뀝니다. 빠르게 찾아 누르세요."

    private var correctButton = 0
    private var score = 0
    private var attempts = 0
    private var started = false

    companion object {
        const val BUTTON_COUNT = 4
        const val MAX_ROUNDS = 10
    }

    override fun start() {
        score = 0
        attempts = 0
        started = true
        nextRound()
    }

    private fun nextRound() {
        correctButton = Random.nextInt(BUTTON_COUNT)
    }

    /**
     * @return true면 정답, false면 오답
     */
    fun press(buttonIndex: Int): Boolean {
        if (!started) return false

        attempts++

        if (buttonIndex == correctButton) {
            score += 100

            if (attempts >= MAX_ROUNDS) {
                started = false
            } else {
                nextRound()
            }

            return true
        }

        score = (score - 50).coerceAtLeast(0)

        if (attempts >= MAX_ROUNDS) {
            started = false
        }

        return false
    }

    fun getCorrectButton(): Int {
        return correctButton
    }

    fun getScore(): Int {
        return score
    }

    fun getAttempts(): Int {
        return attempts
    }

    fun isRunning(): Boolean {
        return started
    }

    override fun reset() {
        correctButton = 0
        score = 0
        attempts = 0
        started = false
    }

    override fun finish(): GameResult {
        started = false

        val coins = (score / 100).coerceAtLeast(1)

        return GameResult(
            gameId = id,
            score = score,
            coins = coins
        )
    }
}
