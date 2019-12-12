package com.cop4485_volkan_bakir.hotmatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class AvatarListGridRecyclerAdapter(private val avatarList: List<Avatar>, private val clickListener: (Avatar) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AvatarListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.avatar_list_item, parent, false))
    }

    override fun getItemCount(): Int = avatarList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val avatarListViewHolder = holder as AvatarListViewHolder
        avatarListViewHolder.bindView(avatarList[position], clickListener)
    }

}