package com.cop4485_volkan_bakir.hotmatch

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage

private const val ONE_MEGA_BYTE: Long = 1024 * 1024

class ScoreBoardRecyclerAdapter(private val userList: MutableList<ScoreBoardUser>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var storage: FirebaseStorage
    private lateinit var bitmapImage: Bitmap

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ScoreBoardRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_sb_list_item, parent, false))
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val scoreBoardViewHolder = holder as ScoreBoardRecyclerViewHolder
        storage = FirebaseStorage.getInstance()
        val pathRef = storage.reference.child(userList[position].userPhotoUrl)
        pathRef.getBytes(ONE_MEGA_BYTE).addOnSuccessListener {
            val options = BitmapFactory.Options()
            bitmapImage = BitmapFactory.decodeByteArray(it, 0, it.size, options)
            scoreBoardViewHolder.bindView(bitmapImage, userList[position].nickname, userList[position].highScore)
        }

    }
}