package com.cop4485_volkan_bakir.hotmatch

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.DialogFragment

class GameOverDialogFragment: DialogFragment() {

    private lateinit var proceedButton: Button
    private var isButtonClicked = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(context).inflate(R.layout.game_over_dialog_fragment, null)
        proceedButton = view.findViewById(R.id.go_proceed_button)
        builder.setView(view)
        val d = builder.create()
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.setCancelable(false)
        proceedButton.setOnClickListener {
            val fragment = UserProfileFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            isButtonClicked = true
            d.dismiss()
        }
        return d
    }

}
