package com.nroncari.tictaccrossandroidapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nroncari.tictaccrossandroidapp.data.websocket.Const
import com.nroncari.tictaccrossandroidapp.data.websocket.StompUtils
import com.nroncari.tictaccrossandroidapp.domain.usecase.ClearGameBoardUseCase
import com.nroncari.tictaccrossandroidapp.domain.usecase.GamePlayUseCase
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePlayPresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.TicToePresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.StompMessage
import java.lang.Error

@SuppressLint("CheckResult")
class GamePlayViewModel(
        private val gamePlayUseCase: GamePlayUseCase,
        private val clearBoardUseCase: ClearGameBoardUseCase
) : ViewModel() {

    private val stompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, Const.address)
    }

    private lateinit var gamePlay: GamePlayPresentation

    private val _game = MutableLiveData<GamePresentation>()
    val game: LiveData<GamePresentation> get() = _game

    private val _winner = MutableLiveData<TicToePresentation?>().apply { value = null }
    val winner: LiveData<TicToePresentation?> get() = _winner

    fun intConnectionWebSocket(sessionGameCode: String) {
        stompClient.connect()
        StompUtils.lifecycle(stompClient)

        stompClient.topic("/topic/game-progress/$sessionGameCode")
                .subscribe { stompMessage: StompMessage ->
                    try {
                        val jsonObject = JSONObject(stompMessage.payload)
                        Log.i(Const.TAG, "ReceiveWS: $jsonObject")
                        val game = Gson().fromJson(jsonObject.toString(), GamePresentation::class.java)
                        _winner.postValue(game.winner)
                        _game.postValue(game)
                    } catch (e: Error) {
                        Log.e(Const.TAG, "Algo de errado não está certo")
                    }

                }
    }

    fun sendGamePlay(gamePlay: GamePlayPresentation) {
        this.gamePlay = gamePlay
        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                gamePlayUseCase(gamePlay)
            }.onSuccess { game ->
                _game.postValue(game)
            }.onFailure { error ->
                Log.e("Error", "Algo de errado não deu certo", error)
            }
        }
    }

    fun clearBoard() {
        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                clearBoardUseCase(gamePlay)
            }.onSuccess { game ->
                _game.postValue(game)
            }.onFailure { error ->
                Log.e("Error", "Algo de errado não deu certo", error)
            }
        }
    }

    override fun onCleared() = stompClient.disconnect()
}