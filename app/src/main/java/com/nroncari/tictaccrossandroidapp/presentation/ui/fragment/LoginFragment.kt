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
        listeners()
    }

    private fun listeners() {
        binding.create.setOnClickListener {
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
    }

    private fun goToConnectGameFragment() {
        val direction = LoginFragmentDirections.actionLoginFragmentToConnectFragment()
        findNavController().navigate(direction)
    }

    private fun goToHashFragment(sessionGameCode: String) {
        val direction = LoginFragmentDirections.actionLoginFragmentToHashFragment(sessionGameCode)
        findNavController().navigate(direction)
    }

    private fun initLoading() {
        binding.loginProgressbar.visibility = VISIBLE
    }

    private fun finishLoading() {
        binding.loginProgressbar.visibility = GONE
    }
}