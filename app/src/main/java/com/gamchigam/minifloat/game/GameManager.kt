package com.gamchigam.minifloat.game

import com.gamchigam.minifloat.game.games.DodgeGame
import com.gamchigam.minifloat.game.games.MemoryGame
import com.gamchigam.minifloat.game.games.NumberOrderGame
import com.gamchigam.minifloat.game.games.RandomButtonGame
import com.gamchigam.minifloat.game.games.ReactionGame
import com.gamchigam.minifloat.game.games.TapRushGame
import com.gamchigam.minifloat.game.games.TargetGame
import com.gamchigam.minifloat.game.games.TimingGame

class GameManager {

    private val games: List<MiniGame> = listOf(
        ReactionGame(),
        TapRushGame(),
        TargetGame(),
        MemoryGame(),
        TimingGame(),
        DodgeGame(),
        NumberOrderGame(),
        RandomButtonGame()
    )

    fun getGames(): List<MiniGame> {
        return games
    }

    fun getGame(id: String): MiniGame? {
        return games.find { it.id == id }
    }
}
