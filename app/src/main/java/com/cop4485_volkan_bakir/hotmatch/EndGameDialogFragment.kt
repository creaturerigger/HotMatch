package com.cop4485_volkan_bakir.hotmatch

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

private const val USER_SCORE = "user_score"
private const val BONUS_POINTS = "bonus_points"

class EndGameDialogFragment: DialogFragment() {

    private lateinit var finalScoreTextView: TextView
    private lateinit var bonusPointsTextView: TextView
    private lateinit var totalScoreTextView: TextView
    private lateinit var proceedButton: Button
    private var userScore: Int = 0
    private var bonusPoints: Int = 0
    private var isButtonClicked = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(context).inflate(R.layout.game_score_fragment, null)
        finalScoreTextView = view.findViewById(R.id.user_final_score_textview)
        bonusPointsTextView = view.findViewById(R.id.user_bonus_points_textview)
        totalScoreTextView = view.findViewById(R.id.user_total_score_textview)
        proceedButton = view.findViewById(R.id.eg_proceed_button)
        builder.setView(view)
        val d = builder.create()
        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.setCancelable(false)
        userScore = arguments?.getSerializable(USER_SCORE) as Int
        bonusPoints = arguments?.getSerializable(BONUS_POINTS) as Int
        proceedButton.setOnClickListener {
            val fragment = UserProfileFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            isButtonClicked = true
            d.dismiss()
        }
        finalScoreTextView.text = userScore.toString()
        bonusPointsTextView.text = bonusPoints.toString()
        totalScoreTextView.text = (userScore + bonusPoints).toString()
        return d
    }

    companion object {
        fun newInstance(userScore: Int, bonusPoints: Int): EndGameDialogFragment {
            val args = Bundle().apply {
                putSerializable(USER_SCORE, userScore)
                putSerializable(BONUS_POINTS, bonusPoints)
            }
            return EndGameDialogFragment().apply {
                arguments = args
            }
        }
    }
}