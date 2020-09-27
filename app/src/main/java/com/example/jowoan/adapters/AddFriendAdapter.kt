package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.User
import kotlinx.android.synthetic.main.item_rv_teman.view.*

class AddFriendAdapter(private val friends: List<User>) :
    RecyclerView.Adapter<AddFriendAdapter.AddFriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_teman, parent, false)
        return AddFriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddFriendViewHolder, position: Int) {
        holder.bind(friends[position])
    }

    override fun getItemCount() = friends.size

    inner class AddFriendViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.img_avatar
        val fullName = view.fullName
        val points = view.points

        fun bind(friend: User) {
            GlideApp.with(view.context).load("http://${friend.avatar?.URL}")
                .placeholder(ImageConfig.defaultAvatar).centerCrop()
                .into(image)
            fullName.text = friend.fullName
            points.text = "${friend.points} poin"
        }
    }

}