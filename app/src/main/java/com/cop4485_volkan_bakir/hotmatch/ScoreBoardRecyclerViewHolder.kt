package com.cop4485_volkan_bakir.hotmatch

import android.graphics.Bitmap
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_sb_list_item.view.*

class ScoreBoardRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindView(profileImage: Bitmap, nickName: String, highScore: String){
        itemView.scb_profile_image.setImageBitmap(profileImage)
        itemView.sb_item_nickname_text_view.text = nickName
        itemView.sb_item_high_score_text_view.text = highScore
    }

}