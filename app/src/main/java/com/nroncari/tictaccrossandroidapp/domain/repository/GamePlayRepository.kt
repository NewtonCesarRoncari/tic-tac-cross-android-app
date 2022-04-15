package com.nroncari.tictaccrossandroidapp.domain.repository

import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePlayPresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation

interface GamePlayRepository {

    suspend fun sendGamePlay(gamePlay: GamePlayRequest): GamePresentation

    suspend fun playAgain(gamePlay: GamePlayRequest): GamePresentation
}