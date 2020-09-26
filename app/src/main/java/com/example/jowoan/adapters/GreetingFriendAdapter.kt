package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import java.util.ArrayList

class GreetingFriendAdapter(private val listGreeting : ArrayList<AktifitasTeman>):
    RecyclerView.Adapter<GreetingFriendAdapter.GreetingViewHolder> (){
   inner class GreetingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
      var Iv_ava : ImageView = itemView.findViewById(R.id.img_avatar)
      var tv_aktifitas : TextView = itemView.findViewById(R.id.activity)
      var tv_username : TextView = itemView.findViewById(R.id.username)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreetingViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_greeting_friend, parent, false)
        return GreetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: GreetingViewHolder, position: Int) {
        val teman = listGreeting[position]
//        holder.Iv_ava = teman.Avatar
        holder.tv_username.text=teman.Nama
        holder.tv_aktifitas.text=teman.Aktifitas
    }

    override fun getItemCount() =listGreeting.size
}