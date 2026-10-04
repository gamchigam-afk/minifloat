package com.gamchigam.minifloat.data

data class GameStats(
    val gameId: String,
    var plays: Int = 0,
    var bestScore: Int = 0,
    var totalCoins: Int = 0
)
