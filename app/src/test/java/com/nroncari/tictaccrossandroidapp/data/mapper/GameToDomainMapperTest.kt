package com.nroncari.tictaccrossandroidapp.data.mapper

import com.nroncari.tictaccrossandroidapp.data.model.GameResponse
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.model.GameStateDomain
import com.nroncari.tictaccrossandroidapp.domain.model.TicToeDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameToDomainMapperTest {

    private val mapper by lazy {
        GameToDomainMapper()
    }

    @Test
    fun `Give success when run map`() {
        // When
        val result = mapper.map(
            GameResponse(
                id = "23",
                state = "NEW",
                amountPlayers = 2,
                winner = "O",
                lastTicToe = "X",
                xScore = 3,
                oScore = 5,
            )
        )

        // Then
        assertTrue(result is GameDomain)
        assertEquals("23", result.id)
        assertEquals(GameStateDomain.NEW, result.state)
        assertEquals(TicToeDomain.O, result.winner)
        assertEquals(2, result.amountPlayers)
        assertEquals(TicToeDomain.X, result.lastTicToe)
        assertEquals(3, result.xScore)
        assertEquals(5, result.oScore)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Give IllegalArgumentException when have empty game state`() {
        mapper.map(GameResponse(id = "23", state = ""))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Give IllegalArgumentException when have invalid game state`() {
        mapper.map(GameResponse(id = "23", state = "EXIT"))
    }

    @Test
    fun `Give null when have invalid lastTicTacToe`() {
        // When
        val result = ('A'..'Z').filterNot { it == 'X' || it == 'O' }.all {
            mapper.map(
                GameResponse(
                    id = "23",
                    state = "RUNNING",
                    winner = it.toString()
                )
            ).winner == null
        }

        // Then
        assertTrue(result)
    }

    @Test
    fun `Give null when have invalid game winner`() {
        // When
        val result = ('A'..'Z').filterNot { it == 'X' || it == 'O' }.all {
            mapper.map(
                GameResponse(
                    id = "23",
                    state = "RUNNING",
                    lastTicToe = it.toString()
                )
            ).lastTicToe == null
        }

        // Then
        assertTrue(result)
    }

}