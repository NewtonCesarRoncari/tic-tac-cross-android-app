package com.nroncari.tictaccrossandroidapp.data.datasource

import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain

interface GameRemoteDataSource {

    suspend fun createGame(): GameDomain

    suspend fun connectGame(gameConnexion: GameConnexionRequest): GameDomain
}