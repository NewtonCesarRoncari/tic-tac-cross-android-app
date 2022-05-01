package com.nroncari.tictaccrossandroidapp.data.repository

import com.nroncari.tictaccrossandroidapp.data.datasource.GameRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.domain.mapper.GameToPresentationMapper
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.repository.GameRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation

class GameRepositoryImpl(
    private val remoteDataSource: GameRemoteDataSource
) : GameRepository {

    override suspend fun createGame(): GameDomain {
        return remoteDataSource.createGame()
    }

    override suspend fun connectGame(gameConnexion: GameConnexionRequest): GameDomain {
        return remoteDataSource.connectGame(gameConnexion)
    }
}