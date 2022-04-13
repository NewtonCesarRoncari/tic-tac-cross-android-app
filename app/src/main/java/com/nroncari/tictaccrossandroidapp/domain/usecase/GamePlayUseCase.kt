package com.nroncari.tictaccrossandroidapp.domain.usecase

import com.nroncari.tictaccrossandroidapp.domain.mapper.GamePlayToRequestMapper
import com.nroncari.tictaccrossandroidapp.domain.repository.GamePlayRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePlayPresentation

class GamePlayUseCase(
        private val repository: GamePlayRepository
) {

    private val mapper = GamePlayToRequestMapper()

    suspend operator fun invoke(gamePlay: GamePlayPresentation) =
            repository.sendGamePlay(mapper.map(gamePlay))
}