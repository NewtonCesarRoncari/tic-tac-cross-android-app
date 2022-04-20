package com.nroncari.tictaccrossandroidapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nroncari.tictaccrossandroidapp.databinding.FragmentLoginBinding
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.SessionGameViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
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

        listeners()
    }

    private fun listeners() {
        binding.create.setOnClickListener {
            disableAnimation()
            initLoading()
            viewModel.createGame()
        }
        binding.connect.setOnClickListener {
            goToConnectGameFragment()
        }
        viewModel.resultSuccess.observe(viewLifecycleOwner, { resultSuccess ->
            if (resultSuccess == true) {
                goToHashFragment(viewModel.game.value!!.id)
            }
            finishLoading()
        })
        viewModel.onRequisitionError.observe(viewLifecycleOwner, { messageError ->
            if (messageError != null)
                initNetworkAnimationError(messageError)
        })
    }

    private fun goToConnectGameFragment() {
        val direction = LoginFragmentDirections.actionLoginFragmentToConnectFragment()
        findNavController().navigate(direction)
    }

    private fun goToHashFragment(sessionGameCode: String) {
        val direction = LoginFragmentDirections.actionLoginFragmentToHashFragment(sessionGameCode)
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
        binding.networkErrorMessage.visibility = View.INVISIBLE
        binding.networkErrorAnimation.visibility = View.INVISIBLE
    }

    private fun initLoading() {
        binding.loginProgressbar.visibility = VISIBLE
    }

    private fun finishLoading() {
        binding.loginProgressbar.visibility = GONE
    }
}