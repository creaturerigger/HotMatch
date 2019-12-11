package com.cop4485_volkan_bakir.hotmatch

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment

private const val TAG = "RegisteredDailogFormat"

class RegisteredDialogFragment: DialogFragment() {

    private lateinit var proceedButton: Button
    private lateinit var mainDialog: ConstraintLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_registered_dialog, null)
        proceedButton = view.findViewById(R.id.proceed_button)
        mainDialog = view.findViewById(R.id.registered_dialog)
        builder.setView(view)
        val d = builder.create()
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        proceedButton.setOnClickListener {
            val fragment = LoginFragment()
            activity?.supportFragmentManager?.beginTransaction()!!
                .replace(R.id.fragment_container, fragment)
                .commit()
            d.dismiss()
        }
        return d
    }
}