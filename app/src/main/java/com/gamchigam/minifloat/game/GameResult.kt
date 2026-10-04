package com.gamchigam.minifloat.game

data class GameResult(
    val gameId: String,
    val score: Int,
    val coins: Int,
    val isNewBest: Boolean = false
)
