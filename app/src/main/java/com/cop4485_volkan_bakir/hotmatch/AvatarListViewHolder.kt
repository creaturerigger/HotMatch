package com.cop4485_volkan_bakir.hotmatch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.avatar_list_item.view.*
import java.util.*

private const val TAG = "AvatarViewHolder"

class AvatarListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    private lateinit var avatarId: UUID

    fun bindView(avatar: Avatar, clickListener: (Avatar) -> Unit ) {
        itemView.avatar_image.setImageResource(avatar.avatarDrawable)
        avatarId = avatar.id
        itemView.setOnClickListener {
            clickListener(avatar)
        }
    }

}