package com.nroncari.tictaccrossandroidapp.domain.usecase

import com.nroncari.tictaccrossandroidapp.domain.mapper.GameConnexionToRequestMapper
import com.nroncari.tictaccrossandroidapp.domain.repository.GameRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GameConnexionPresentation

class ConnectGameUseCase(
    private val repository: GameRepository
) {

    private val mapper = GameConnexionToRequestMapper()

    suspend operator fun invoke(gameConnexion: GameConnexionPresentation) =
        repository.connectGame(mapper.map(gameConnexion))

}
