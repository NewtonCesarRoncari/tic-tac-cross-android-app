package com.nroncari.tictaccrossandroidapp.data.datasource

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.data.model.GameResponse
import com.nroncari.tictaccrossandroidapp.data.service.GamePlayService
import com.nroncari.tictaccrossandroidapp.domain.model.GameStateDomain
import com.nroncari.tictaccrossandroidapp.domain.model.TicToeDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

const val gameId = "677yy"
const val gameState = "RUNNING"

@ExperimentalCoroutinesApi
class GamePlayRemoteDataSourceImplTest {

    private val service: GamePlayService = mock()

    private val dataSource by lazy {
        GamePlayRemoteDataSourceImpl(service)
    }

    private val gamePlay = GamePlayRequest(gameId, "X", 2, 2)

    @Test
    fun `When send a game from data source should return success`() = runBlockingTest {
        // Given
        whenever(service.sendGamePlay(gamePlay)).thenReturn(
            GameResponse(id = gameId, lastTicToe = "X", state = gameState)
        )

        // When
        val result = dataSource.sendGamePlay(gamePlay)

        // Then
        verify(service).sendGamePlay(gamePlay)
        assertEquals(gameId, result.id)
        assertEquals(TicToeDomain.X, result.lastTicToe)
        assertEquals(GameStateDomain.RUNNING, result.state)
    }

    @Test
    fun `When playAgain from data source should return success`() = runBlockingTest {
        // Given
        whenever(service.playAgain(gamePlay)).thenReturn(
            GameResponse(id = gameId, lastTicToe = "O", state = gameState)
        )

        // When
        val result = dataSource.playAgain(gamePlay)

        // Then
        verify(service).playAgain(gamePlay)
        assertEquals(gameId, result.id)
        assertEquals(TicToeDomain.O, result.lastTicToe)
        assertEquals(GameStateDomain.RUNNING, result.state)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Give exception when service returns invalid game state in sendGamePlay method`() =
        runBlockingTest {
            // Given
            whenever(service.sendGamePlay(gamePlay)).thenReturn(
                GameResponse(id = gameId, lastTicToe = "", state = "")
            )

            // When
            dataSource.sendGamePlay(gamePlay)
        }

    @Test(expected = IllegalArgumentException::class)
    fun `Give exception when service returns invalid game state in playAgain method`() =
        runBlockingTest {
            // Given
            whenever(service.playAgain(gamePlay)).thenReturn(
                GameResponse(id = gameId, lastTicToe = "", state = "")
            )

            // When
            dataSource.playAgain(gamePlay)
        }
}