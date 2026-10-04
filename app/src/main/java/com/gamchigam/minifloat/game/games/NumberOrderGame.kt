package com.gamchigam.minifloat.game.games

import com.gamchigam.minifloat.game.GameResult

class NumberOrderGame : MiniGame {

    override val id = "number_order"
    override val name = "🔢 Number Order"
    override val description = "숫자를 1부터 순서대로 빠르게 누르세요."

    private var numbers: List<Int> = emptyList()
    private var nextNumber = 1
    private var correct = 0
    private var started = false

    override fun start() {
        numbers = (1..9).shuffled()
        nextNumber = 1
        correct = 0
        started = true
    }

    fun getNumbers(): List<Int> {
        return numbers
    }

    fun press(number: Int): Boolean {
        if (!started) return false

        if (number == nextNumber) {
            correct++
            nextNumber++

            if (nextNumber > numbers.size) {
                started = false
            }

            return true
        }

        started = false
        return false
    }

    fun getNextNumber(): Int {
        return nextNumber
    }

    fun getCorrectCount(): Int {
        return correct
    }

    override fun reset() {
        numbers = emptyList()
        nextNumber = 1
        correct = 0
        started = false
    }

    override fun finish(): GameResult {
        started = false

        val score = correct * 150
        val coins = (correct / 2).coerceAtLeast(1)

        return GameResult(
            gameId = id,
            score = score,
            coins = coins
        )
    }
}
