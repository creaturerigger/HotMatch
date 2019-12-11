package com.cop4485_volkan_bakir.hotmatch

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "AvatarImageDialog"

class AvatarImageDialogFragment: DialogFragment() {

    interface Callbacks {
        fun onAvatarClicked(avatarImageId: Int)
    }

    private lateinit var descriptionTextView: TextView
    private lateinit var avatarRecyclerView: RecyclerView
    private lateinit var d: AlertDialog
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = targetFragment as Callbacks?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_avatar_image_dialog, null)
        descriptionTextView = view.findViewById(R.id.avatar_description_textview)
        avatarRecyclerView = view.findViewById(R.id.avatar_list_recyclerview)
        builder.setView(view)
        d = builder.create()
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initView()
        return d
    }

    private fun initView() {
        avatarRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        avatarRecyclerView.addItemDecoration(GridItemDecoration(25, 2))
        val avatarViewModel = AvatarViewModel()
        val avatarListData = avatarViewModel.avatarList
        val avatarListAdapter = AvatarListGridRecyclerAdapter(avatarListData) { avatar: Avatar -> onAvatarClicked(avatar) }
        avatarRecyclerView.adapter = avatarListAdapter
    }

    private fun onAvatarClicked(avatar: Avatar) {
        val avatarImageId = avatar.avatarDrawable
        targetFragment?.let {
            fragment ->
            (fragment as Callbacks).onAvatarClicked(avatarImageId)
        }
        d.dismiss()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}