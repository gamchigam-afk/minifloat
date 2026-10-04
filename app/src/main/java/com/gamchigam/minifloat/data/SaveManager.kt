package com.gamchigam.minifloat.data

import android.content.Context

class SaveManager(context: Context) {

    private val prefs = context.getSharedPreferences(
        "minifloat_save",
        Context.MODE_PRIVATE
    )

    fun loadPlayerData(): PlayerData {
        return PlayerData(
            coins = prefs.getInt("coins", 0),
            totalPlays = prefs.getInt("totalPlays", 0),
            totalScore = prefs.getInt("totalScore", 0),
            bestScore = prefs.getInt("bestScore", 0)
        )
    }

    fun savePlayerData(data: PlayerData) {
        prefs.edit()
            .putInt("coins", data.coins)
            .putInt("totalPlays", data.totalPlays)
            .putInt("totalScore", data.totalScore)
            .putInt("bestScore", data.bestScore)
            .apply()
    }

    fun getBestScore(gameId: String): Int {
        return prefs.getInt("best_$gameId", 0)
    }

    fun saveBestScore(gameId: String, score: Int) {
        if (score > getBestScore(gameId)) {
            prefs.edit()
                .putInt("best_$gameId", score)
                .apply()
        }
    }

    fun addCoins(amount: Int) {
        val current = prefs.getInt("coins", 0)

        prefs.edit()
            .putInt("coins", current + amount)
            .apply()
    }

    fun getCoins(): Int {
        return prefs.getInt("coins", 0)
    }
}
