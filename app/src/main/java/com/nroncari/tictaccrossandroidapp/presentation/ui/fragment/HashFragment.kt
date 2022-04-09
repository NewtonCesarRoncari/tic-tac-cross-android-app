package com.nroncari.tictaccrossandroidapp.presentation.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.nroncari.tictaccrossandroidapp.R
import com.nroncari.tictaccrossandroidapp.databinding.FragmentHashBinding
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.SessionGameViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HashFragment : Fragment() {

    private val viewBinding by lazy {
        FragmentHashBinding.inflate(layoutInflater)
    }
    private val viewModel: SessionGameViewModel by sharedViewModel()
    private var toggleColor = true
    private val listCircles by lazy {
        listOf(
            viewBinding.circleA1,
            viewBinding.circleA2,
            viewBinding.circleA3,
            viewBinding.circleB1,
            viewBinding.circleB2,
            viewBinding.circleB3,
            viewBinding.circleC1,
            viewBinding.circleC2,
            viewBinding.circleC3,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listeners()
    }

    private fun listeners() {
        listCircles.forEach { markButton(it) }

        viewBinding.reset.setOnClickListener {
            listCircles.forEach { circleButton ->
                circleButton.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.circle_item
                )
            }
        }
        viewModel.game.observe(viewLifecycleOwner, {
            viewBinding.token.text = it.id.substring(0,8)
        })
        viewBinding.copy.setOnClickListener {
            val clipBoard: ClipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("TextView", viewBinding.token.text.toString())
            clipBoard.setPrimaryClip(clip)

            Snackbar.make(requireView(), "Copied", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun markButton(button: Button) {
        button.setOnClickListener {
            button.background =
                ContextCompat.getDrawable(
                    requireContext(),
                    if (toggleColor) R.drawable.x_item_blue else R.drawable.circle_item_red
                )
            toggleColor = !toggleColor
        }
    }
}