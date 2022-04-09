package com.nroncari.tictaccrossandroidapp.data.websocket

import android.annotation.SuppressLint
import android.util.Log
import com.nroncari.tictaccrossandroidapp.data.websocket.Const.TAG
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

object StompUtils {
    @SuppressLint("CheckResult")
    fun lifecycle(stompClient: StompClient) {
        stompClient.lifecycle().subscribe { lifecycleEvent: LifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> Log.d(TAG, "Stomp connection opened")
                LifecycleEvent.Type.ERROR -> Log.e(
                    TAG,
                    "Error",
                    lifecycleEvent.exception
                )
                LifecycleEvent.Type.CLOSED -> Log.d(TAG, "Stomp connection closed")
                else -> {}
            }
        }
    }
}