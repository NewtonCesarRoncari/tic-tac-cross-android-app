package com.nroncari.tictaccrossandroidapp.presentation.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nroncari.tictaccrossandroidapp.R
import com.nroncari.tictaccrossandroidapp.databinding.FragmentHashBinding
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePlayPresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GamePresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.GameStatePresentation
import com.nroncari.tictaccrossandroidapp.presentation.model.TicToePresentation
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.GamePlayViewModel
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.SessionGameViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HashFragment : Fragment() {

    private val viewBinding by lazy { FragmentHashBinding.inflate(layoutInflater) }
    private val viewModel: SessionGameViewModel by sharedViewModel()
    private val gamePlayViewModel: GamePlayViewModel by viewModel()
    private val args by navArgs<HashFragmentArgs>()
    private val listCircles by lazy {
        mapOf(
                0.0 to viewBinding.circleA1,
                0.1 to viewBinding.circleA2,
                0.2 to viewBinding.circleA3,
                1.0 to viewBinding.circleB1,
                1.1 to viewBinding.circleB2,
                1.2 to viewBinding.circleB3,
                2.0 to viewBinding.circleC1,
                2.1 to viewBinding.circleC2,
                2.2 to viewBinding.circleC3,
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
        viewBinding.token.text = args.sessionGameCode
        gamePlayViewModel.intConnectionWebSocket(args.sessionGameCode)
    }

    private fun listeners() {

        listCircles.forEach { map ->
            val resource = if (viewModel.ticToe.value == 1) R.drawable.circle_item_red else R.drawable.x_item_blue
            map.value.setOnClickListener {
                if (gamePlayViewModel.yourTime) {
                    markButton(map.value, resource)
                    val coordinate = map.key.toString().split(".")

                    gamePlayViewModel.sendGamePlay(GamePlayPresentation(
                            gameId = args.sessionGameCode,
                            type = viewModel.ticToe.desc,
                            coordinateX = coordinate.first().toInt(),
                            coordinateY = coordinate.last().toInt()
                    ))
                    gamePlayViewModel.disableButtons()
                }
            }
        }

        viewBinding.reset.setOnClickListener {
            gamePlayViewModel.playAgain()
        }

        viewBinding.copy.setOnClickListener {
            configureClipBoard()
        }

        gamePlayViewModel.isNewGame.observe(viewLifecycleOwner, {
            clearBoard()
        })

        gamePlayViewModel.game.observe(viewLifecycleOwner, { game ->
            gamePlayViewModel.checkStateGame(GameStatePresentation.NEW)
            when (game.winner) {
                TicToePresentation.O -> showOWinner()
                TicToePresentation.X -> showXWInner()
                else -> clearWinners()
            }
            gamePlayViewModel.configureYourTime(viewModel.ticToe)
            setScoreGame(game)
            fillBoard(game)
        })

        gamePlayViewModel.secondPlayerConnected.observe(viewLifecycleOwner, {
            gamePlayViewModel.checkIAmFirstPlayer(TicToePresentation.X)
        })

        gamePlayViewModel.showSecondPlayerSnackBar.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), "Player 2 connected", Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun setScoreGame(game: GamePresentation) {
        if (game.xscore >= viewBinding.xAmountScoreBoard.text.toString().toInt())
            viewBinding.xAmountScoreBoard.text = game.xscore.toString()
        if (game.oscore >= viewBinding.oAmountScoreBoard.text.toString().toInt())
            viewBinding.oAmountScoreBoard.text = game.oscore.toString()
    }

    private fun fillBoard(game: GamePresentation) {
        game.board.mapIndexed { x, ints ->
            ints.mapIndexed { y, ticTacToe ->
                val position = "$x.$y".toDouble()

                if (ticTacToe == TicToePresentation.O.value)
                    markButton(listCircles[position]!!, R.drawable.circle_item_red)

                if (ticTacToe == TicToePresentation.X.value)
                    markButton(listCircles[position]!!, R.drawable.x_item_blue)
            }
        }
    }

    private fun configureClipBoard() {
        val clipBoard: ClipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("TextView", viewBinding.token.text.toString())
        clipBoard.setPrimaryClip(clip)

        Snackbar.make(requireView(), "Copied", Snackbar.LENGTH_SHORT).show()
    }

    private fun showXWInner() {
        viewBinding.xWinner.visibility = VISIBLE
        gamePlayViewModel.disableButtons()
    }

    private fun showOWinner() {
        viewBinding.oWinner.visibility = VISIBLE
        gamePlayViewModel.disableButtons()
    }

    private fun clearBoard() {
        listCircles.values.forEach { circleButton ->
            circleButton.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.circle_item
            )
            circleButton.isClickable = true
        }
        gamePlayViewModel.enableButtons()
        clearWinners()
    }

    private fun clearWinners() {
        viewBinding.oWinner.visibility = INVISIBLE
        viewBinding.xWinner.visibility = INVISIBLE
    }

    private fun markButton(button: AppCompatImageView, resource: Int) {
        button.background = ContextCompat.getDrawable(requireContext(), resource)
        button.isClickable = false
    }
}