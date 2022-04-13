package com.nroncari.tictaccrossandroidapp.data.datasource

import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain

interface GamePlayRemoteDataSource {

    suspend fun sendGamePlay(gamePlay: GamePlayRequest): GameDomain

    suspend fun clearBoard(gamePlay: GamePlayRequest): GameDomain
}