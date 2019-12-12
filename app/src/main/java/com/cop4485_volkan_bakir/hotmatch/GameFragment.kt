package com.cop4485_volkan_bakir.hotmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class GameFragment: Fragment() {

    private lateinit var scoreTextView: TextView
    private lateinit var cardRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        scoreTextView = view.findViewById(R.id.game_score_textview)
        cardRecyclerView = view.findViewById(R.id.game_cards_rv)
        return view
    }
}