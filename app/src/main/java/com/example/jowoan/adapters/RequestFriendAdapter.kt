package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.User
import kotlinx.android.synthetic.main.item_friend_request.view.*

class RequestFriendAdapter(private val friends: List<User>, private val action: Action) :
    RecyclerView.Adapter<RequestFriendAdapter.RequestFriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestFriendViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_friend_request, parent, false)
        return RequestFriendViewHolder(view, action)
    }

    override fun onBindViewHolder(holder: RequestFriendViewHolder, position: Int) {
        holder.bind(friends[position], position)
    }

    override fun getItemCount() = friends.size

    inner class RequestFriendViewHolder(val view: View, val action: Action) :
        RecyclerView.ViewHolder(view) {
        private val avatar = view.avatar
        private val fullName = view.fullName
        private val requestLabel = view.request_label
        private val accept = view.button_accept
        private val reject = view.button_reject

        fun bind(friend: User, position: Int) {
            GlideApp.with(view.context).load("http://${friend.avatar?.URL}").centerCrop()
                .placeholder(ImageConfig.defaultAvatar)
                .into(avatar)
            fullName.text = friend.fullName
            accept.setOnClickListener {
                action.accept(friend, position)
            }
            reject.setOnClickListener {
                action.reject(friend, position)
            }
        }
    }

    interface Action {
        fun accept(friend: User, position: Int)
        fun reject(friend: User, position: Int)
    }

}