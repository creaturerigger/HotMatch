package com.cop4485_volkan_bakir.hotmatch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GameCardRecyclerAdapter(private val gameCardList: List<GameCard>,
                              private var cardImageList: MutableList<CardImage>,
                              private val clickListener: (ImageView, CardImage, GameCard) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.game_card_list_item, parent, false))
    }

    override fun getItemCount(): Int = gameCardList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val gameCardViewHolder = holder as CardListViewHolder

        gameCardViewHolder.bindView(gameCardList[position], cardImageList[position], clickListener)
    }

}