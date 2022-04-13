package com.nroncari.tictaccrossandroidapp.data.service

import com.nroncari.tictaccrossandroidapp.data.model.GamePlayRequest
import com.nroncari.tictaccrossandroidapp.data.model.GameResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GamePlayService {

    @POST("gameplay/")
    suspend fun sendGamePlay(@Body gamePlay: GamePlayRequest): GameResponse

    @POST("playagain")
    suspend fun clearBoard(@Body gamePlay: GamePlayRequest): GameResponse
}