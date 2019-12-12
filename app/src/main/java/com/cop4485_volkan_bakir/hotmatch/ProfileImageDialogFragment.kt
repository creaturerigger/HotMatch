package com.cop4485_volkan_bakir.hotmatch

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment


class ProfileImageDialogFragment: DialogFragment() {

    interface Callbacks {
        fun onCameraButtonClicked()
        fun onAvatarsButtonClicked()
        fun onGalleryButtonClicked()
    }

    private var callbacks: Callbacks? = null

    private lateinit var avatarsButton: Button
    private lateinit var cameraButton: Button
    private lateinit var galleryButton: Button
    private lateinit var mainDialog: ConstraintLayout


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = targetFragment as Callbacks?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_profile_image, null)
        avatarsButton = view.findViewById(R.id.pp_btn_avatars) as Button
        cameraButton = view.findViewById(R.id.pp_btn_camera) as Button
        galleryButton = view.findViewById(R.id.pp_btn_gallery) as Button
        mainDialog = view.findViewById(R.id.pp_constraint_layout) as ConstraintLayout
        builder.setView(view)
        val d = builder.create()
        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        avatarsButton.setOnClickListener {
            callbacks?.onAvatarsButtonClicked()
            d.dismiss()
        }
        cameraButton.setOnClickListener {
            callbacks?.onCameraButtonClicked()
            d.dismiss()
        }
        galleryButton.setOnClickListener {
            callbacks?.onGalleryButtonClicked()
            d.dismiss()
        }

        return d
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }



}