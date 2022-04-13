package com.nroncari.tictaccrossandroidapp.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nroncari.tictaccrossandroidapp.databinding.FragmentConnectGameBinding
import com.nroncari.tictaccrossandroidapp.presentation.model.GameConnexionPresentation
import com.nroncari.tictaccrossandroidapp.presentation.validator.CodeGameValidator
import com.nroncari.tictaccrossandroidapp.presentation.validator.Validator
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.SessionGameViewModel
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

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            if (validAllFields()) {
                initLoading()
                viewModel.connectGame(
                        GameConnexionPresentation(binding.connectCode.text.toString().trim())
                )
            }
        }
        viewModel.resultSuccess.observe(viewLifecycleOwner, { resultSuccess ->
            if (resultSuccess) {
                goToHashFragment(binding.connectCode.text.toString().trim())
            }
            finishLoading()
        })
    }

    private fun goToHashFragment(sessionGameCode: String) {
        val direction = ConnectFragmentDirections.actionConnectFragmentToHashFragment(sessionGameCode)
        findNavController().navigate(direction)
    }

    private fun initLoading() {
        binding.connectProgressbar.visibility = View.VISIBLE
    }

    private fun finishLoading() {
        binding.connectProgressbar.visibility = View.GONE
    }
}