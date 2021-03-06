package com.cop4485_volkan_bakir.hotmatch

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

private const val TAG = "UserProfileFragment"
private const val ONE_MEGABYTE: Long = 1024 * 1024

class UserProfileFragment : Fragment() {

    private lateinit var circleImageView: CircleImageView
    private lateinit var highScoreTextView: TextView
    private lateinit var latestScoreTextView: TextView
    private lateinit var userLevelTextView: TextView
    private lateinit var userNickname: TextView
    private lateinit var startGameButton: Button
    private lateinit var viewScoreBoardButton: Button


    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, null)
        circleImageView = view.findViewById(R.id.sb_profile_image)
        highScoreTextView = view.findViewById(R.id.pf_high_score)
        latestScoreTextView = view.findViewById(R.id.pf_latest_score)
        userLevelTextView = view.findViewById(R.id.pf_level)
        userNickname = view.findViewById(R.id.pf_user_nickname)
        startGameButton = view.findViewById(R.id.pf_start_game_button)
        viewScoreBoardButton = view.findViewById(R.id.pf_view_score_board)
        startGameButton.isEnabled = false
        viewScoreBoardButton.isEnabled = false
        startGameButton.setOnClickListener {
            val fragment = GameFragment.newInstance(userLevelTextView.text.toString())
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        viewScoreBoardButton.setOnClickListener {
            val fragment = ScoreBoardFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        updateUI()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun updateUI() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            databaseReference =
                FirebaseDatabase.getInstance().getReference("users").child(user.uid)

            storage = FirebaseStorage.getInstance()
            val httpsReference = storage.getReferenceFromUrl(user.photoUrl.toString())
            Log.d(TAG, "user photo url is: ${user.photoUrl}")

            httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                val options = BitmapFactory.Options()
                val bitmapImage = BitmapFactory.decodeByteArray(it, 0, it.size, options)
                circleImageView.setImageBitmap(bitmapImage)
            }
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val gameUser = dataSnapshot.getValue(GameUser::class.java)
                        Log.d(TAG, "gameUser retrieved from database as: ${gameUser.toString()}")

                        highScoreTextView.text = gameUser?.highScore.toString()
                        latestScoreTextView.text = gameUser?.score.toString()
                        userLevelTextView.text = gameUser?.level.toString()
                        userNickname.text = gameUser?.nickName.toString()
                        startGameButton.isEnabled = true
                        viewScoreBoardButton.isEnabled = true
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }
}