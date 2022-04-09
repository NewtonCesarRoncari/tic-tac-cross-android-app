package com.nroncari.tictaccrossandroidapp.domain.usecase

import com.nroncari.tictaccrossandroidapp.domain.repository.GameRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation

class CreateGameUseCase(
    private val repository: GameRepository
) {

    suspend operator fun invoke(): GamePresentation {
        return repository.createGame()
    }
}