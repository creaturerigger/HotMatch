package com.cop4485_volkan_bakir.hotmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "GameFragment"

class GameFragment: Fragment() {

    private val gameCardViewModel: GameCardViewModel by lazy {
        ViewModelProviders.of(this).get(GameCardViewModel::class.java)
    }

    private val cardImageList: CardImageViewModel by lazy {
        ViewModelProviders.of(this).get(CardImageViewModel::class.java)
    }

    private lateinit var scoreTextView: TextView
    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var gameCardAdapter: GameCardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        scoreTextView = view.findViewById(R.id.game_score_textview)
        cardRecyclerView = view.findViewById(R.id.game_cards_rv)
        initRecyclerView()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun initRecyclerView() {
        cardRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            val gameItemDecoration = GridItemDecoration(25, 2)
            addItemDecoration(gameItemDecoration)
            val cardListData = gameCardViewModel.levelOneCardList
            val cardImageListData = cardImageList.cardImageListLeveOne
            gameCardAdapter = GameCardRecyclerAdapter(cardListData, cardImageListData) { cardImageView: ImageView, cardImage: CardImage -> onGameCardClicked(cardImageView, cardImage) }
            adapter = gameCardAdapter
        }
    }

    private fun onGameCardClicked(cardImageView: ImageView, cardImage: CardImage) {
        cardImageView.setImageResource(cardImage.cardImageId)
    }
}