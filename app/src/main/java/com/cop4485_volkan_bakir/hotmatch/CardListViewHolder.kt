package com.cop4485_volkan_bakir.hotmatch

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.game_card_list_item.view.*

class CardListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindView(gameCard: GameCard, cardImage: CardImage, clickListener: (ImageView, CardImage, GameCard) -> Unit) {
        itemView.card_image.setImageResource(gameCard.imageDrawableId)
        itemView.card_image.tag = gameCard.id
        itemView.setOnClickListener {
            clickListener(itemView.card_image, cardImage, gameCard)
        }
    }
}