package com.nroncari.tictaccrossandroidapp.data.service

import com.nroncari.tictaccrossandroidapp.data.model.GameConnexionRequest
import com.nroncari.tictaccrossandroidapp.data.model.GameResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GameService {

    @GET("start/")
    suspend fun createGame(): GameResponse

    @POST("connect/")
    suspend fun connectGame(@Body gameConnexion: GameConnexionRequest): GameResponse
}