package com.nroncari.tictaccrossandroidapp.data.datasource

import com.nroncari.tictaccrossandroidapp.data.mapper.GameToDomainMapper
import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.data.service.GameService
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain

class GameRemoteDataSourceImpl(
    private val service: GameService
) : GameRemoteDataSource {

    private val gameMapper = GameToDomainMapper()

    override suspend fun createGame(): GameDomain {
        return gameMapper.map(service.createGame())
    }

    override suspend fun connectGame(gameConnexion: GameConnexionRequest): GameDomain {
        return gameMapper.map(service.connectGame(gameConnexion))
    }
}