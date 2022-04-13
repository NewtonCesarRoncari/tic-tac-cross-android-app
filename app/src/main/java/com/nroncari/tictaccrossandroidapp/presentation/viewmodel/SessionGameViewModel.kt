package com.nroncari.tictaccrossandroidapp.presentation.viewmodel

import android.util.Log
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

class SessionGameViewModel(
    private val createGameUseCase: CreateGameUseCase,
    private val connectGameUseCase: ConnectGameUseCase
): ViewModel() {

    private val _resultSuccess = MutableLiveData<Boolean>().apply { value = false }
    val resultSuccess: LiveData<Boolean> get() = _resultSuccess

    private val _game = MutableLiveData<GamePresentation>()
    val game: LiveData<GamePresentation> get() = _game

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
                Log.e("Error", "Algo de errado não deu certo", error)
                _resultSuccess.postValue(false)
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
                Log.e("Error", "Algo de errado não deu certo", error)
                _resultSuccess.postValue(false)
            }
        }
    }
}