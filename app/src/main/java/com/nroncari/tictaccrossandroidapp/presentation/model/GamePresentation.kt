package com.nroncari.tictaccrossandroidapp.presentation.model

class GamePresentation(
    val id: String,
    var amountPlayers: Int = 0,
    val lastTicToe: TicToePresentation? = null,
    var state: GameStatePresentation,
    val board: Array<IntArray> = Array(3) { IntArray(3) },
    var winner: TicToePresentation? = null,
    val xscore: Int = 0,
    val oscore: Int = 0
)