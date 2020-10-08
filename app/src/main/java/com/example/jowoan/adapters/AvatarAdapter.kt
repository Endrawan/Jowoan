package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R.layout.item_avatar
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.Avatar
import kotlinx.android.synthetic.main.item_avatar.view.*

class AvatarAdapter(val avatars: List<Avatar>, val action: Action) :
    RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        return AvatarViewHolder(
            LayoutInflater.from(parent.context).inflate(item_avatar, parent, false)
        )
    }

    override fun getItemCount(): Int = avatars.size

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        holder.bind(avatars[position], action)
    }

    class AvatarViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image
        val name = view.name
        val category = view.category
        val price = view.price

        fun bind(avatar: Avatar, action: Action) {
            if (!avatar.name.isNullOrEmpty())
                name.text = avatar.name
            else name.text = "Lorem Ipsum"

            GlideApp.with(view.context).load("http://${avatar.URL}").centerCrop()
                .placeholder(ImageConfig.imageNotFound)
                .into(image)

            category.text = "Avatar"

            price.text = "${avatar.price} poin"

            view.setOnClickListener {
                action.clicked(avatar)
            }
        }
    }

    interface Action {
        fun clicked(avatar: Avatar)
    }

}