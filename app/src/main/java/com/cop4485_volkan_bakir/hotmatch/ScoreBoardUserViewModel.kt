package com.cop4485_volkan_bakir.hotmatch

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

private const val TAG = "ScoreBoardUserViewModel"

class ScoreBoardUserViewModel : ViewModel() {

    private lateinit var databaseReference: DatabaseReference
    var userListData: MutableList<ScoreBoardUser> = mutableListOf()

    private fun getUsers(userList: MutableList<ScoreBoardUser>, scoreBoardUserRef: ScoreBoardUser){
        userList.add(scoreBoardUserRef)
    }

    fun getUsers() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (data in dataSnapshot.children) {
                    val gameUser = data.getValue(GameUser::class.java)
                    Log.d(TAG, "gameUser is: $gameUser")
                    val scoreBoardUser = ScoreBoardUser(
                        gameUser!!.nickName,
                        "profile_images/${gameUser.userId}.jpg",
                        gameUser.highScore.toString()
                    )
                    Log.d(TAG, "scoreBoard user is: ${scoreBoardUser.nickname}")
                    getUsers(userListData, scoreBoardUser)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}