package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.User
import kotlinx.android.synthetic.main.item_friend_activity.view.*

class FriendActivityAdapter(private val friends: List<User>, private val action: Action) :
    RecyclerView.Adapter<FriendActivityAdapter.FriendActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendActivityViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend_activity, parent, false)
        return FriendActivityViewHolder(view, action)
    }

    override fun onBindViewHolder(holder: FriendActivityViewHolder, position: Int) {
        holder.bind(friends[position])
    }

    override fun getItemCount() = friends.size

    inner class FriendActivityViewHolder(val view: View, val action: Action) :
        RecyclerView.ViewHolder(view) {
        private val avatar = view.avatar
        private val fullName = view.fullName
        private val activityLabel = view.activity_label
        private val celebrate = view.celebrate

        fun bind(friend: User) {
            GlideApp.with(view.context).load("http://${friend.avatar?.URL}").centerCrop()
                .placeholder(ImageConfig.defaultAvatar)
                .into(avatar)
            fullName.text = friend.fullName
            celebrate.setOnClickListener {
                action.celebrate(friend)
            }
        }
    }

    interface Action {
        fun celebrate(friend: User)
    }

}