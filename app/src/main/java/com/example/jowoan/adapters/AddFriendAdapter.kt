package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import java.util.ArrayList

class AddFriendAdapter (private val listaddfriend : ArrayList<Teman>):
    RecyclerView.Adapter<AddFriendAdapter.AddFriendViewHolder>() {

    inner class AddFriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.findViewById(R.id.username)
        var tvPoints: TextView = itemView.findViewById(R.id.point_pts)
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_teman,parent,false)
        return AddFriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddFriendViewHolder, position: Int) {
        val teman = listaddfriend[position]
//        holder.imgAvatar = teman.Avatar
        holder.tvNama.text=teman.Nama
        holder.tvPoints.text=teman.Poin

    }

    override fun getItemCount() = listaddfriend.size

}