package com.example.jowoan.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import com.example.jowoan.adapters.AddFriendAdapter
import com.example.jowoan.adapters.Teman
import com.example.jowoan.adapters.dataTeman
import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriendActivity : AppCompatActivity() {

    private lateinit var rvAddFriend: RecyclerView
    private var list: ArrayList<Teman> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)


        buttonBack.setOnClickListener {
            finish()
        }

        rvAddFriend = findViewById(R.id.recyclerView)

        list.addAll(dataTeman.listData)
        rvAddFriend.layoutManager = LinearLayoutManager(this)
        val addFriendAdapter = AddFriendAdapter(list)
        rvAddFriend.adapter = addFriendAdapter

    }
}