package com.nroncari.tictaccrossandroidapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nroncari.tictaccrossandroidapp.domain.usecase.ConnectGameUseCase
import com.nroncari.tictaccrossandroidapp.domain.usecase.CreateGameUseCase
import com.nroncari.tictaccrossandroidapp.presentation.model.GameConnexionPresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.TicToePresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class SessionGameViewModel(
    private val createGameUseCase: CreateGameUseCase,
    private val connectGameUseCase: ConnectGameUseCase
) : ViewModel() {

    private val _resultSuccess = MutableLiveData<Boolean?>().apply { value = false }
    val resultSuccess: LiveData<Boolean?> get() = _resultSuccess

    private val _game = MutableLiveData<GamePresentation?>()
    val game: LiveData<GamePresentation?> get() = _game

    private val _onRequisitionError = MutableLiveData<String?>()
    val onRequisitionError: LiveData<String?> get() = _onRequisitionError

    lateinit var ticToe: TicToePresentation

    fun createGame() {
        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                createGameUseCase()
            }.onSuccess { game ->
                ticToe = TicToePresentation.X
                _game.postValue(game)
                _resultSuccess.postValue(true)
            }.onFailure { error ->
                onError(error)
            }
        }
    }

    fun connectGame(gameConnexion: GameConnexionPresentation) {
        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                connectGameUseCase(gameConnexion)
            }.onSuccess { game ->
                ticToe = TicToePresentation.O
                _game.postValue(game)
                _resultSuccess.postValue(true)
            }.onFailure { error ->
                onError(error)
            }
        }
    }

    private fun onError(error: Throwable) {
        val messageError: String = when (error) {
            is HttpException -> "code: ${error.code()}, ${
                error.response()!!.errorBody()!!.string()
            }"
            is SocketTimeoutException -> "Timeout: failed to connect to server"
            is ConnectException -> "Network: failed to connect to server, verify your connexion"
            else -> "Unexpected error"
        }
        _onRequisitionError.postValue(messageError)
        _resultSuccess.postValue(false)
    }

    override fun onCleared() {
        _resultSuccess.postValue(null)
        _game.postValue(null)
    }
}