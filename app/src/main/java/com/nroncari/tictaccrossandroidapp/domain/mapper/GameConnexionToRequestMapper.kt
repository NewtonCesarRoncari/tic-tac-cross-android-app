package com.nroncari.tictaccrossandroidapp.domain.mapper

import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.presentation.model.GameConnexionPresentation
import com.nroncari.tictaccrossandroidapp.utils.Mapper

class GameConnexionToRequestMapper : Mapper<GameConnexionPresentation, GameConnexionRequest> {

    override fun map(source: GameConnexionPresentation): GameConnexionRequest {
        return GameConnexionRequest(
            gameId = source.gameId
        )
    }
}