package com.nroncari.tictaccrossandroidapp.data.repository

import com.nroncari.tictaccrossandroidapp.data.datasource.GamePlayRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.domain.mapper.GameToPresentationMapper
import com.nroncari.tictaccrossandroidapp.domain.repository.GamePlayRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation

class GamePlayRepositoryImpl(
        private val remoteDataSource: GamePlayRemoteDataSource
) : GamePlayRepository {

    private val mapper = GameToPresentationMapper()

    override suspend fun sendGamePlay(gamePlay: GamePlayRequest): GamePresentation {
        return mapper.map(remoteDataSource.sendGamePlay(gamePlay))
    }

    override suspend fun playAgain(gamePlay: GamePlayRequest): GamePresentation {
        return mapper.map(remoteDataSource.playAgain(gamePlay))
    }
}