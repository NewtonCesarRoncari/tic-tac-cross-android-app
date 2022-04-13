package com.nroncari.tictaccrossandroidapp.data.datasource

import com.nroncari.tictaccrossandroidapp.data.mapper.GameToDomainMapper
import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.data.service.GamePlayService
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain

class GamePlayRemoteDataSourceImpl(
        private val service: GamePlayService
) : GamePlayRemoteDataSource {

    private val gameMapper = GameToDomainMapper()

    override suspend fun sendGamePlay(gamePlay: GamePlayRequest): GameDomain {
        return gameMapper.map(service.sendGamePlay(gamePlay))
    }

    override suspend fun clearBoard(gamePlay: GamePlayRequest): GameDomain {
        return gameMapper.map(service.clearBoard(gamePlay))
    }
}