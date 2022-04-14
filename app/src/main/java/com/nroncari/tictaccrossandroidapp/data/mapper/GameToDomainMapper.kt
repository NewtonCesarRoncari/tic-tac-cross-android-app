package com.nroncari.tictaccrossandroidapp.data.mapper

import com.nroncari.tictaccrossandroidapp.data.model.GameResponse
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.model.GameStateDomain
import com.nroncari.tictaccrossandroidapp.domain.model.TicToeDomain
import com.nroncari.tictaccrossandroidapp.utils.Mapper

class GameToDomainMapper : Mapper<GameResponse, GameDomain> {

    override fun map(source: GameResponse): GameDomain {
        return GameDomain(
                id = source.id,
                amountPlayers = source.amountPlayers,
                lastTicToe = parseTicToe(source.lastTicToe ?: ""),
                state = returnGameState(source.state),
                board = source.board,
                winner = parseTicToe(source.winner ?: ""),
                xScore = source.xScore,
                oScore = source.oScore
        )
    }

    private fun returnGameState(state: String): GameStateDomain {
        return when (state) {
            "NEW" -> GameStateDomain.NEW
            "RUNNING" -> GameStateDomain.RUNNING
            "FINISHED" -> GameStateDomain.FINISHED
            else -> throw IllegalArgumentException()
        }
    }

    private fun parseTicToe(winner: String): TicToeDomain? {
        return when (winner) {
            "X" -> TicToeDomain.X
            "O" -> TicToeDomain.O
            else -> null
        }
    }
}