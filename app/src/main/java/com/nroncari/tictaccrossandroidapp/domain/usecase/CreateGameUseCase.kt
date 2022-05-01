package com.nroncari.tictaccrossandroidapp.domain.usecase

import com.nroncari.tictaccrossandroidapp.domain.mapper.GameToPresentationMapper
import com.nroncari.tictaccrossandroidapp.domain.repository.GameRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation

class CreateGameUseCase(
    private val repository: GameRepository
) {

    private val mapper = GameToPresentationMapper()

    suspend operator fun invoke(): GamePresentation {
        return mapper.map(repository.createGame())
    }
}