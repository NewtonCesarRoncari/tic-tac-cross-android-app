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
import com.nroncari.tictaccrossandroidapp.domain.usecase.PlayAgainUseCase
import com.nroncari.tictaccrossandroidapp.domain.usecase.GamePlayUseCase
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePlayPresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GameStatePresentation
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
        private val playAgainUseCase: PlayAgainUseCase
) : ViewModel() {

    private val stompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, Const.address)
    }

    private lateinit var gamePlay: GamePlayPresentation

    private var _yourTime = true
    val yourTime: Boolean get() = _yourTime

    private val _showSecondPlayerSnackBar = MutableLiveData<Boolean>()
    val showSecondPlayerSnackBar: LiveData<Boolean> get() = _showSecondPlayerSnackBar

    private val _isNewGame = MutableLiveData<Boolean>()
    val isNewGame: LiveData<Boolean> get() = _isNewGame

    private val _secondPlayerConnected = MutableLiveData<Boolean>()
    val secondPlayerConnected: LiveData<Boolean> get() = _secondPlayerConnected

    private val _game = MutableLiveData<GamePresentation>()
    val game: LiveData<GamePresentation> get() = _game

    fun intConnectionWebSocket(sessionGameCode: String) {
        stompClient.connect()
        StompUtils.lifecycle(stompClient)

        stompClient.topic("/topic/game-progress/$sessionGameCode")
                .subscribe { stompMessage: StompMessage ->
                    try {
                        val jsonObject = JSONObject(stompMessage.payload)
                        Log.i(Const.TAG, "ReceiveWS: $jsonObject")
                        val game = Gson().fromJson(jsonObject.toString(), GamePresentation::class.java)
                        _game.postValue(game)
                    } catch (e: Error) {
                        Log.e(Const.TAG, "Algo de errado n??o est?? certo")
                    }

                }

        stompClient.topic("/topic/game-progress/connected")
                .subscribe { stompMessage: StompMessage ->
                    try {
                        val jsonObject = JSONObject(stompMessage.payload)
                        Log.i(Const.TAG, "ReceiveWS: $jsonObject")
                        _secondPlayerConnected.postValue(true)
                    } catch (e: Error) {
                        Log.e(Const.TAG, "Algo de errado n??o est?? certo")
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
                Log.e("Error", "Algo de errado n??o deu certo", error)
            }
        }
    }

    fun playAgain() {
        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                playAgainUseCase(gamePlay)
            }.onSuccess { game ->
                _game.postValue(game)
            }.onFailure { error ->
                Log.e("Error", "Algo de errado n??o deu certo", error)
            }
        }
    }

    fun disableButtons() {
        _yourTime = false
    }

    fun enableButtons() {
        _yourTime = true
    }

    fun configureYourTime(ticToe: TicToePresentation) {
        if (_game.value!!.winner == null)
            _yourTime = _game.value!!.lastTicToe != ticToe
    }

    fun checkStateGame(new: GameStatePresentation) {
        if (_game.value!!.state == new)
            _isNewGame.postValue(true)
    }

    fun checkIAmFirstPlayer(ticToe: TicToePresentation) {
        if (secondPlayerConnected.value == true && ticToe == TicToePresentation.X)
            _showSecondPlayerSnackBar.postValue(true)
    }

    override fun onCleared() = stompClient.disconnect()
}