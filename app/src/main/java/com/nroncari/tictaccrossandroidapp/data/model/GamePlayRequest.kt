package com.nroncari.tictaccrossandroidapp.data.model

class GamePlayRequest(
        private val gameId: String,
        private val type: String,
        private val coordinateX: Int,
        private val coordinateY: Int,
)