package com.nroncari.tictaccrossandroidapp.domain.model

data class GameDomain(
    val id: String,
    var amountPlayers: Int = 0,
    var state: GameStateDomain,
    val board: Array<IntArray> = Array(3) { IntArray(3) },
    var winner: TicToeDomain? = null
)