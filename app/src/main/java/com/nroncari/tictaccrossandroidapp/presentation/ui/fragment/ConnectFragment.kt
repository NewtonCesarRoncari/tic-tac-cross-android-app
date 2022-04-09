package com.nroncari.tictaccrossandroidapp.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nroncari.tictaccrossandroidapp.data.websocket.Const
import com.nroncari.tictaccrossandroidapp.data.websocket.StompUtils
import com.nroncari.tictaccrossandroidapp.databinding.FragmentConnectGameBinding
import com.nroncari.tictaccrossandroidapp.presentation.model.GameConnexionPresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.TicToePresentation
import com.nroncari.tictaccrossandroidapp.presentation.validator.CodeGameValidator
import com.nroncari.tictaccrossandroidapp.presentation.validator.Validator
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.SessionGameViewModel
import com.nroncari.tictaccrossandroidapp.presentation.ui.fragment.ConnectFragmentDirections
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.StompMessage


class ConnectFragment : Fragment() {

    private val binding by lazy {
        FragmentConnectGameBinding.inflate(layoutInflater)
    }

    private val validators = mutableListOf<Validator>()
    private val viewModel: SessionGameViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listener()
        validateFieldGameCode()

        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Const.address)
        Toast.makeText(requireContext(), "Start connecting to server", Toast.LENGTH_SHORT).show()
        stompClient.connect()
        StompUtils.lifecycle(stompClient)

        Log.i(Const.TAG, "Subscribe chat endpoint to receive response")
        stompClient.topic("/topic/game-progress/a0293c03")
            .subscribe { stompMessage: StompMessage ->
                val jsonObject = JSONObject(stompMessage.payload)
                Log.i(Const.TAG, "ReceiveWS: $jsonObject")

            }
        // stompClient.disconnect();
    }

    private fun validateFieldGameCode() {
        val field = binding.connectCodeTextInputLayout.editText
        val validator = CodeGameValidator(binding.connectCodeTextInputLayout)
        validators.add(validator)
        field!!.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) validator.isValid()
        }
    }

    private fun validAllFields(): Boolean {
        var formIsValid = true
        for (validator in validators) {
            if (!validator.isValid()) {
                formIsValid = false
            }
        }
        return formIsValid
    }

    private fun listener() {
        binding.connect.setOnClickListener {
            if (validAllFields()) {
                initLoading()
                viewModel.connectGame(
                    GameConnexionPresentation(
                        binding.connectCode.text.toString().trim()
                    )
                )
            }
        }
        viewModel.resultSuccess.observe(viewLifecycleOwner, { resultSuccess ->
            if (resultSuccess) {
                viewModel.ticToe = TicToePresentation.O
                goToHashFragment()
            }
            finishLoading()
        })
    }

    private fun goToHashFragment() {
        val direction = ConnectFragmentDirections.actionConnectFragmentToHashFragment()
        findNavController().navigate(direction)
    }

    private fun initLoading() {
        binding.connectProgressbar.visibility = View.VISIBLE
    }

    private fun finishLoading() {
        binding.connectProgressbar.visibility = View.GONE
    }
}