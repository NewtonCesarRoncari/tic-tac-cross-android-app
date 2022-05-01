package com.nroncari.tictaccrossandroidapp.data.datasource

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nroncari.tictaccrossandroidapp.data.mapper.GameToDomainMapper
import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.data.model.GameResponse
import com.nroncari.tictaccrossandroidapp.data.service.GameService
import com.nroncari.tictaccrossandroidapp.domain.model.GameDomain
import com.nroncari.tictaccrossandroidapp.domain.model.GameStateDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class GameRemoteDataSourceImplTest {

    private val service: GameService = mock()

    private val dataSource by lazy {
        GameRemoteDataSourceImpl(service)
    }

    @Test
    fun `When get a game from data source should return success`() = runBlockingTest {
        // Given
        whenever(service.createGame()).thenReturn(
            GameResponse(id = "23", state = "NEW")
        )

        // When
        val result = dataSource.createGame()

        // Then
        verify(service).createGame()
        assertEquals("23", result.id)
        assertEquals(GameStateDomain.NEW, result.state)
    }

    @Test
    fun `When connect a game from data source should return success`() = runBlockingTest {
        //Given
        val gameConnexion = GameConnexionRequest(gameId = "46")
        whenever(service.connectGame(gameConnexion)).thenReturn(
            GameResponse(id = "46", state = "RUNNING")
        )

        // When
        val result = dataSource.connectGame(gameConnexion)

        // Then
        verify(service).connectGame(gameConnexion)
        assertEquals("46", result.id)
        assertEquals(GameStateDomain.RUNNING, result.state)
    }
}