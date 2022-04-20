package com.nroncari.tictaccrossandroidapp.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.nroncari.tictaccrossandroidapp.databinding.FragmentConnectGameBinding
import com.nroncari.tictaccrossandroidapp.presentation.model.GameConnexionPresentation
import com.nroncari.tictaccrossandroidapp.presentation.validator.CodeGameValidator
import com.nroncari.tictaccrossandroidapp.presentation.validator.Validator
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.SessionGameViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ConnectFragment : Fragment() {

    private val binding by lazy { FragmentConnectGameBinding.inflate(layoutInflater) }

    private val validators = mutableListOf<Validator>()
    private val viewModel: SessionGameViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.networkErrorAnimation.setAnimation("anim/network_error.json")

        listener()
        validateFieldGameCode()
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
            disableAnimation()
            if (validAllFields()) {
                initLoading()
                viewModel.connectGame(
                    GameConnexionPresentation(binding.connectCode.text.toString().trim())
                )
            }
        }
        viewModel.resultSuccess.observe(viewLifecycleOwner, { resultSuccess ->
            if (resultSuccess == true) {
                goToHashFragment(binding.connectCode.text.toString().trim())
            }
            finishLoading()
        })
        viewModel.onRequisitionError.observe(viewLifecycleOwner, { messageError ->
            if (messageError != null)
            initNetworkAnimationError(messageError)
        })
    }

    private fun goToHashFragment(sessionGameCode: String) {
        val direction =
            ConnectFragmentDirections.actionConnectFragmentToHashFragment(sessionGameCode)
        findNavController().navigate(direction)
    }

    private fun initNetworkAnimationError(messageError: String) {
        with(binding.networkErrorAnimation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
        binding.networkErrorMessage.visibility = VISIBLE
        binding.networkErrorMessage.text = messageError
    }

    private fun disableAnimation() {
        binding.networkErrorMessage.visibility = INVISIBLE
        binding.networkErrorAnimation.visibility = INVISIBLE
    }

    private fun initLoading() {
        binding.connectProgressbar.visibility = VISIBLE
    }

    private fun finishLoading() {
        binding.connectProgressbar.visibility = GONE
    }
}