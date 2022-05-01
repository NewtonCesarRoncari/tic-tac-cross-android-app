package com.nroncari.tictaccrossandroidapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nroncari.tictaccrossandroidapp.data.datasource.GameRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.model.GameStateDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GameRepositoryImplTest {

    private val dataSource: GameRemoteDataSource = mock()

    private val repository by lazy {
        GameRepositoryImpl(dataSource)
    }

    @Test
    fun `When get a game from repository should return success`() = runBlockingTest {
        // Given
        whenever(dataSource.createGame()).thenReturn(
            GameDomain(id = "12", state = GameStateDomain.NEW)
        )

        // When
        val result = repository.createGame()

        // Then
        verify(dataSource).createGame()
        assertEquals("12", result.id)
        assertEquals(GameStateDomain.NEW, result.state)
    }

    @Test
    fun `When connect a game from repository should return success`() = runBlockingTest {
        //Given
        val gameConnexion = GameConnexionRequest(gameId = "42")
        whenever(dataSource.connectGame(gameConnexion)).thenReturn(
            GameDomain(
                id = "42", state = GameStateDomain.RUNNING,
            )
        )

        // When
        val result = repository.connectGame(gameConnexion)

        // Then
        verify(dataSource).connectGame(gameConnexion)
        assertEquals("42", result.id)
        assertEquals(GameStateDomain.RUNNING, result.state)
    }
}