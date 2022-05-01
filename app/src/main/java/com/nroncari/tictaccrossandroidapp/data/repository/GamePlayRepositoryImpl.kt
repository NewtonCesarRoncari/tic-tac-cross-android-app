package com.nroncari.tictaccrossandroidapp.data.repository

import com.nroncari.tictaccrossandroidapp.data.datasource.GamePlayRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.domain.mapper.GameToPresentationMapper
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.repository.GamePlayRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation

class GamePlayRepositoryImpl(
        private val remoteDataSource: GamePlayRemoteDataSource
) : GamePlayRepository {

    override suspend fun sendGamePlay(gamePlay: GamePlayRequest): GameDomain {
        return remoteDataSource.sendGamePlay(gamePlay)
    }

    override suspend fun playAgain(gamePlay: GamePlayRequest): GameDomain {
        return remoteDataSource.playAgain(gamePlay)
    }
}