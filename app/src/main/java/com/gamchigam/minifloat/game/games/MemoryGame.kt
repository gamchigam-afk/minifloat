package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.GameResult

class MemoryGame : MiniGame {

    override val id = "memory"
    override val name = "🧠 Memory"
    override val description = "순서를 기억하고 그대로 입력하세요."

    private var sequence: List<Int> = emptyList()
    private var currentIndex = 0
    private var correct = 0
    private var started = false

    override fun start() {
        sequence = List(5) {
            (1..4).random()
        }

        currentIndex = 0
        correct = 0
        started = true
    }

    fun getSequence(): List<Int> {
        return sequence
    }

    fun input(value: Int): Boolean {
        if (!started || currentIndex >= sequence.size) {
            return false
        }

        val isCorrect = sequence[currentIndex] == value

        if (isCorrect) {
            correct++
            currentIndex++

            if (currentIndex >= sequence.size) {
                started = false
            }
        } else {
            started = false
        }

        return isCorrect
    }

    fun getProgress(): Int {
        return currentIndex
    }

    fun getCorrectCount(): Int {
        return correct
    }

    override fun reset() {
        sequence = emptyList()
        currentIndex = 0
        correct = 0
        started = false
    }

    override fun finish(): GameResult {
        started = false

        val score = correct * 200
        val coins = (correct * 2).coerceAtLeast(1)

        return GameResult(
            gameId = id,
            score = score,
            coins = coins
        )
    }
}
