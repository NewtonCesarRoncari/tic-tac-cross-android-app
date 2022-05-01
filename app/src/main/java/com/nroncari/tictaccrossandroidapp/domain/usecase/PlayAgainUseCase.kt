package com.nroncari.tictaccrossandroidapp.domain.usecase

import com.nroncari.tictaccrossandroidapp.domain.mapper.GamePlayToRequestMapper
import com.nroncari.tictaccrossandroidapp.domain.mapper.GameToPresentationMapper
import com.nroncari.tictaccrossandroidapp.domain.repository.GamePlayRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePlayPresentation

class PlayAgainUseCase(
        private val repository: GamePlayRepository
) {

    private val mapperRequest = GamePlayToRequestMapper()
    private val mapperPresentation = GameToPresentationMapper()

    suspend operator fun invoke(gamePlay: GamePlayPresentation) =
        mapperPresentation.map(repository.playAgain(mapperRequest.map(gamePlay)))
}