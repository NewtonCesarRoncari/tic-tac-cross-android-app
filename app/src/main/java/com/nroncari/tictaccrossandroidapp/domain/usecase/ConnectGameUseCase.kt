package com.nroncari.tictaccrossandroidapp.domain.usecase

import com.nroncari.tictaccrossandroidapp.domain.mapper.GameConnexionToRequestMapper
import com.nroncari.tictaccrossandroidapp.domain.mapper.GameToPresentationMapper
import com.nroncari.tictaccrossandroidapp.domain.repository.GameRepository
import com.nroncari.tictaccrossandroidapp.presentation.model.GameConnexionPresentation

class ConnectGameUseCase(
    private val repository: GameRepository
) {

    private val mapperRequest = GameConnexionToRequestMapper()
    private val mapperDomain = GameToPresentationMapper()

    suspend operator fun invoke(gameConnexion: GameConnexionPresentation) =
        mapperDomain.map(repository.connectGame(mapperRequest.map(gameConnexion)))

}
