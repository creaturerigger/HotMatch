package com.cop4485_volkan_bakir.hotmatch

import android.os.Bundle
import android.os.CountDownTimer
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private const val TAG = "GameFragment"
private const val ARG_LEVEL = "user_level"
private const val END_GAME_DIALOG = 123

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
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var gameTimerMinute: TextView
    private lateinit var gameTimerSecond: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountDown: Long = 180000
    private var countDownInterval: Long = 1000

    private var clickedCards: MutableList<Pair<CardImage, ImageView>> = mutableListOf()
    private var levelCounter: Int = 3
    private var gameScore: Int = 0
    private var scorePoint: Int = 1
    private var bonusPoints: Int = 0
    private var bonusMultiplier: Float = 0.0F
    private var isGameFinished: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userLevel = arguments?.getSerializable(ARG_LEVEL) as String
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        scoreTextView = view.findViewById(R.id.game_score_textview)
        cardRecyclerView = view.findViewById(R.id.game_cards_rv)
        gameTimerMinute = view.findViewById(R.id.game_timer_minute)
        gameTimerSecond = view.findViewById(R.id.game_timer_second)
        levelCounter = setLevelCounter(userLevel)
        scorePoint = setScorePoint(userLevel)
        bonusMultiplier = setBonusMultiplier(userLevel)
        initRecyclerView()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                val second = (millisUntilFinished % 60000) / 1000
                val minute = millisUntilFinished / 60000
                var secondText = second.toString()
                val minuteText = "0${minute}"
                if (second < 10) {
                    secondText = "0$secondText"
                }
                gameTimerMinute.text = minuteText
                gameTimerSecond.text = secondText
                if(isGameFinished) {
                    Log.d(TAG, "Game is finished set to true")
                    bonusPoints = ((millisUntilFinished / 1000) * bonusMultiplier).toInt()
                }
            }

            override fun onFinish() {
                val gameOverDialogFragment = GameOverDialogFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, gameOverDialogFragment)
                    .commit()
            }
        }
        countDownTimer.start()
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
        if (cardImageView.tag == gameCard.imageDrawableId && clickedCards.size <= 2) {
            cardImageView.setImageResource(cardImage.cardImageId)
            cardImageView.tag = cardImage.cardImageId
            clickedCards.add(Pair(cardImage, cardImageView))
            Handler().postDelayed({
                if (clickedCards.size == 2) {
                    if (clickedCards[0].second.tag == clickedCards[1].second.tag) {

                        removeItemsFromList(clickedCards)
                        levelCounter -= 1
                        Log.d(TAG, "level counter is: $levelCounter")
                        gameScore += scorePoint
                        scoreTextView.text = gameScore.toString()
                        if (levelCounter == 0) {
                            removeItemsFromList(clickedCards)
                            endGame()
                        }
                    } else {
                        returnDefaultState(clickedCards, gameCard)
                    }
                }
            }, 600)
        }
    }

    private fun removeItemsFromList(list: MutableList<Pair<CardImage, ImageView>>) {
        for (mPair in list) {
            mPair.second.setOnClickListener(null)
            mPair.second.tag = mPair.first.cardImageId
        }
        list.clear()
    }

    private fun returnDefaultState(
        list: MutableList<Pair<CardImage, ImageView>>,
        gameCard: GameCard
    ) {
        for (mPair in list) {
            mPair.second.setImageResource(gameCard.imageDrawableId)
            mPair.second.tag = gameCard.imageDrawableId
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

    private fun setBonusMultiplier(level: String): Float {
        var multiplier = 0.0F
        when (level) {
            "1" -> {
                multiplier = 0.2F
            }
            "2" -> {
                multiplier = 0.3F
            }
            "3" -> {
                multiplier = 0.4F
            }
            "4" -> {
                multiplier = 0.5F
            }
        }
        return multiplier
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

    private fun endGame() {
        isGameFinished = true
        Handler().postDelayed({
            Log.d(TAG, "Bonus points are: $bonusPoints")

            auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            if (user != null) {
                Log.d(TAG,"user id is ${user.uid}")
                gameScore += bonusPoints
                databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.uid)
                databaseReference.addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.d(TAG, "Database operation has been cancelled!")
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val gameUser = dataSnapshot.getValue(GameUser::class.java)

                            if (gameUser != null) {
                                val highScore = gameUser.highScore
                                val currentLevel = gameUser.level

                                if (gameScore > highScore!!) {
                                    databaseReference.child("highScore").setValue(gameScore)
                                    databaseReference.child("score").setValue(gameScore)
                                } else {
                                    databaseReference.child("score").setValue(gameScore)
                                }
                                if (currentLevel!! <= 3){
                                    databaseReference.child("level").setValue(userLevel.toInt() + 1)
                                }

                            }

                        }
                    }

                })

            }
            countDownTimer.cancel()
            val endGameDialogFragment = EndGameDialogFragment.newInstance(scoreTextView.text.toString().toInt(), bonusPoints)
            endGameDialogFragment.apply {
                setTargetFragment(this@GameFragment, END_GAME_DIALOG)
                show(this@GameFragment.requireFragmentManager(), TAG)
            }
        }, 1200)
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