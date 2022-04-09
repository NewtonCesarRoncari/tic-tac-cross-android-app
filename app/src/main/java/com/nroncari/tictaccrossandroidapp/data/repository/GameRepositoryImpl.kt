package com.nroncari.tictaccrossandroidapp.data.repository

import com.nroncari.tictaccrossandroidapp.data.datasource.GameRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.domain.mapper.GameToPresentationMapper
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation
import com.nroncari.tictaccrossandroidapp.domain.repository.GameRepository

class GameRepositoryImpl(
    private val remoteDataSource: GameRemoteDataSource
) : GameRepository {

    private val mapper = GameToPresentationMapper()

    override suspend fun createGame(): GamePresentation {
        return mapper.map(remoteDataSource.createGame())
    }

    override suspend fun connectGame(gameConnexion: GameConnexionRequest): GamePresentation {
        return mapper.map(remoteDataSource.connectGame(gameConnexion))
    }
}