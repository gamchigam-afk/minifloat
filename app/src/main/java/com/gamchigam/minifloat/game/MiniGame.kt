package com.gamchigam.minifloat.game

interface MiniGame {

    val id: String
    val name: String
    val description: String

    fun start()

    fun reset()

    fun finish(): GameResult
}
