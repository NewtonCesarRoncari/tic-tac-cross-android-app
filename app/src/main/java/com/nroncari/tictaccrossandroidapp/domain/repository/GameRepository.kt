package com.nroncari.tictaccrossandroidapp.domain.repository

import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation

interface GameRepository {

    suspend fun createGame(): GameDomain

    suspend fun connectGame(gameConnexion: GameConnexionRequest): GameDomain
}