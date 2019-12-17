package com.cop4485_volkan_bakir.hotmatch

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "ScoreBoardFragment"

class ScoreBoardFragment: Fragment() {

    private lateinit var scoreBoardRecylerView: RecyclerView
    private lateinit var scoreBoardAdapter: ScoreBoardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score_board, container, false)
        scoreBoardRecylerView = view.findViewById(R.id.sb_recycler_view)
        initRecyclerView()
        return view
    }

    private fun initRecyclerView() {
        val scoreBoardViewModel = ScoreBoardUserViewModel()
        scoreBoardViewModel.getUsers()
        Handler().postDelayed({
            val userListData = scoreBoardViewModel.userListData
            userListData.sortByDescending {
                scoreBoardUser ->
                scoreBoardUser.highScore
            }
            Log.d(TAG, "user data is: $userListData")
            val itemDecoration = GridItemDecoration(10, 1)
            scoreBoardRecylerView.apply {
                layoutManager = GridLayoutManager(requireActivity(), 1)
                addItemDecoration(itemDecoration)
                scoreBoardAdapter = ScoreBoardRecyclerAdapter(userListData)
                adapter = scoreBoardAdapter
            }
        }, 2000)


    }

}