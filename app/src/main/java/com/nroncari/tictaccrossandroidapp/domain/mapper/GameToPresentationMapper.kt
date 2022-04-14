package com.nroncari.tictaccrossandroidapp.domain.mapper

import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.model.GameStateDomain
import com.nroncari.tictaccrossandroidapp.domain.model.TicToeDomain
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GameStatePresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.TicToePresentation
import com.nroncari.tictaccrossandroidapp.utils.Mapper

class GameToPresentationMapper : Mapper<GameDomain, GamePresentation> {

    override fun map(source: GameDomain): GamePresentation {
        return GamePresentation(
                id = source.id,
                amountPlayers = source.amountPlayers,
                lastTicToe = parseTicToe(source.lastTicToe),
                state = returnGameState(source.state),
                board = source.board,
                winner = parseTicToe(source.winner),
                xscore = source.xScore,
                oscore = source.oScore
        )
    }

    private fun returnGameState(state: GameStateDomain): GameStatePresentation {
        return when (state) {
            GameStateDomain.NEW -> GameStatePresentation.NEW
            GameStateDomain.RUNNING -> GameStatePresentation.RUNNING
            GameStateDomain.FINISHED -> GameStatePresentation.FINISHED
        }
    }

    private fun parseTicToe(winner: TicToeDomain?): TicToePresentation? {
        return when (winner) {
            TicToeDomain.X -> TicToePresentation.X
            TicToeDomain.O -> TicToePresentation.O
            else -> null
        }
    }
}