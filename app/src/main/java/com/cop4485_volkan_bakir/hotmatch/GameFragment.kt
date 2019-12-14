package com.cop4485_volkan_bakir.hotmatch

import android.os.Bundle
import android.os.Handler
import android.util.Log
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
private const val ARG_LEVEL = "user_level"

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
    private lateinit var userLevel: String

    private var clickedCards: MutableList<Pair<CardImage, ImageView>> = mutableListOf()
    private var levelCounter: Int = 3
    private var gameScore: Int = 0
    private var scorePoint: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userLevel = arguments?.getSerializable(ARG_LEVEL) as String
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        scoreTextView = view.findViewById(R.id.game_score_textview)
        cardRecyclerView = view.findViewById(R.id.game_cards_rv)
        levelCounter = setLevelCounter(userLevel)
        scorePoint = setScorePoint(userLevel)
        initRecyclerView()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun initRecyclerView() {
        val cardListData = selectCardListData(userLevel)
        val cardImageListData = selectCardImageData(userLevel)
        val columnCount = setColumnCount(userLevel)
        val gameItemDecoration = GridItemDecoration(25, columnCount)
        cardImageListData.shuffle()
        cardRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), columnCount)
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
        if (clickedCards.size <= 2) {
            cardImageView.setImageResource(cardImage.cardImageId)
            cardImageView.tag = cardImage.cardImageId
            clickedCards.add(Pair(cardImage, cardImageView))
            Handler().postDelayed({
                if (clickedCards.size == 2) {
                    if (clickedCards[0].second.tag == clickedCards[1].second.tag) {

                        removeItemsFromList(clickedCards)
                        levelCounter--
                        if (levelCounter == 0) {
                            removeItemsFromList(clickedCards)
                        }
                        gameScore += scorePoint
                        scoreTextView.text = gameScore.toString()
                    } else {
                        returnDefaultState(clickedCards, gameCard)
                    }
                }
            }, 600)
        }

        Log.d(TAG, "clicked cards size is ${clickedCards.size}")
    }

    private fun removeItemsFromList(list: MutableList<Pair<CardImage, ImageView>>) {
        for (mPair in list) {
            mPair.second.setOnClickListener(null)
        }
        list.clear()
    }

    private fun returnDefaultState(
        list: MutableList<Pair<CardImage, ImageView>>,
        gameCard: GameCard
    ) {
        for (mPair in list) {
            mPair.second.setImageResource(gameCard.imageDrawableId)
        }
        list.clear()
    }

    private fun selectCardListData(level: String): List<GameCard> {
        return when (level) {
            "1" -> gameCardViewModel.levelOneCardList
            "2" -> gameCardViewModel.levelTwoCardList
            "3" -> gameCardViewModel.levelThreeCardList
            "4" -> gameCardViewModel.levelFourCardList
            else -> listOf()
        }
    }

    private fun selectCardImageData(level: String): MutableList<CardImage> {
        return when (level) {
            "1" -> cardImageList.cardImageListLevelOne
            "2" -> cardImageList.cardImageListLevelTwo
            "3" -> cardImageList.cardImageListLevelThree
            "4" -> cardImageList.cardImageListLevelFour
            else -> mutableListOf()
        }
    }

    private fun setColumnCount(level: String): Int {
        return if (level == "1" || level == "2") {
            2
        } else if (level == "3") {
            3
        } else if (level == "4") {
            4
        } else {
            0
        }
    }

    private fun setLevelCounter(level: String): Int {
        var counter = 0
        when (level) {
            "1" -> {
                counter = 3
            }
            "2" -> {
                counter = 4
            }
            "3" -> {
                counter = 6
            }
            "4" -> {
                counter = 8
            }
        }
        return counter
    }

    private fun setScorePoint(level: String): Int {
        var multiplier = 1
        when (level) {
            "1" -> {
                multiplier = 3
            }
            "2" -> {
                multiplier = 6
            }
            "3" -> {
                multiplier = 12
            }
            "4" -> {
                multiplier = 16
            }
        }
        return multiplier
    }


    companion object {
        fun newInstance(level: String): GameFragment {
            val args = Bundle().apply {
                putSerializable(ARG_LEVEL, level)
            }
            return GameFragment().apply {
                arguments = args
            }
        }

    }

}