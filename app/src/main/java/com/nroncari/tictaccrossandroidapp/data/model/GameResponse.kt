package com.nroncari.tictaccrossandroidapp.data.model

data class GameResponse(
    val id: String,
    var amountPlayers: Int = 0,
    val lastTicToe: String? = null,
    var state: String,
    val board: Array<IntArray> = Array(3) { IntArray(3) },
    var winner: String? = null,
    val xScore: Int = 0,
    val oScore: Int = 0
)