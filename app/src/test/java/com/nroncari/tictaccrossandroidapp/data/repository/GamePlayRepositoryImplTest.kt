package com.nroncari.tictaccrossandroidapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nroncari.tictaccrossandroidapp.data.datasource.GamePlayRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.model.GameStateDomain
import com.nroncari.tictaccrossandroidapp.domain.model.TicToeDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*

const val gameId = "56436j"

@ExperimentalCoroutinesApi
class GamePlayRepositoryImplTest {

    private val dataSource: GamePlayRemoteDataSource = mock()

    private val repository by lazy {
        GamePlayRepositoryImpl(dataSource)
    }

    private val gamePlay = GamePlayRequest(gameId, "X", 2, 2)

    @Test
    fun `When send a game from repository should return success`() = runBlockingTest {
        // Given
        whenever(dataSource.sendGamePlay(gamePlay)).thenReturn(
            GameDomain(id = gameId, lastTicToe = TicToeDomain.X, state = GameStateDomain.RUNNING)
        )

        // When
        val result = repository.sendGamePlay(gamePlay)

        // Then
        verify(dataSource).sendGamePlay(gamePlay)
        assertEquals(gameId, result.id)
        assertEquals(TicToeDomain.X, result.lastTicToe)
        assertEquals(GameStateDomain.RUNNING, result.state)
    }

    @Test
    fun `When playAgain from repository should return success`() = runBlockingTest {
        // Given
        whenever(dataSource.playAgain(gamePlay)).thenReturn(
            GameDomain(id = gameId, lastTicToe = TicToeDomain.O, state = GameStateDomain.RUNNING)
        )

        // When
        val result = repository.playAgain(gamePlay)

        // Then
        verify(dataSource).playAgain(gamePlay)
        assertEquals(gameId, result.id)
        assertEquals(TicToeDomain.O, result.lastTicToe)
        assertEquals(GameStateDomain.RUNNING, result.state)
    }
}