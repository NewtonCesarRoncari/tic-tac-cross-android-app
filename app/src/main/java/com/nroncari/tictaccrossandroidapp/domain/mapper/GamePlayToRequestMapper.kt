package com.nroncari.tictaccrossandroidapp.domain.mapper

import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePlayPresentation
import com.nroncari.tictaccrossandroidapp.utils.Mapper

class GamePlayToRequestMapper : Mapper<GamePlayPresentation, GamePlayRequest> {

    override fun map(source: GamePlayPresentation): GamePlayRequest {
        return GamePlayRequest(
                source.gameId,
                source.type,
                source.coordinateX,
                source.coordinateY
        )
    }
}