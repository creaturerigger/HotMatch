package com.cop4485_volkan_bakir.hotmatch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "GameFragment"

class GameFragment : Fragment() {

    private val gameCardViewModel: GameCardViewModel by lazy {
        ViewModelProviders.of(this).get(GameCardViewModel::class.java)
    }

    private val cardImageList: CardImageViewModel by lazy {
        ViewModelProviders.of(this).get(CardImageViewModel::class.java)
    }

    private lateinit var scoreTextView: TextView
    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var gameCardAdapter: GameCardRecyclerAdapter

    private var clickedCards: MutableList<Pair<CardImage, ImageView>> = mutableListOf()
    private var levelCounter: Int = 3

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
        val cardListData = gameCardViewModel.levelOneCardList
        val cardImageListData = cardImageList.cardImageListLevelOne
        val gameItemDecoration = GridItemDecoration(25, 2)
        cardImageListData.shuffle()
        cardRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            addItemDecoration(gameItemDecoration)
            gameCardAdapter = GameCardRecyclerAdapter(cardListData, cardImageListData)
            { cardImageView: ImageView, cardImage: CardImage, gameCard: GameCard ->
                onGameCardClicked(
                    cardImageView,
                    cardImage,
                    gameCard
                )
            }
            adapter = gameCardAdapter
        }
    }

    private fun onGameCardClicked(
        cardImageView: ImageView,
        cardImage: CardImage,
        gameCard: GameCard
    ) {

        if (clickedCards.size == 2) {
            if (clickedCards[0].second.tag == clickedCards[1].second.tag) {

                for (mPair in clickedCards) {
                    mPair.second.setOnClickListener(null)
                }
                clickedCards.clear()
                levelCounter--
            } else {

                for (mPair in clickedCards) {
                    mPair.second.setImageResource(gameCard.imageDrawableId)
                }
                clickedCards.clear()
            }
        }
        if (clickedCards.size <= 2) {
            cardImageView.setImageResource(cardImage.cardImageId)
            cardImageView.tag = cardImage.cardImageId
            clickedCards.add(Pair(cardImage, cardImageView))
        }

        Log.d(TAG, "clicked cards size is ${clickedCards.size}")
    }


}