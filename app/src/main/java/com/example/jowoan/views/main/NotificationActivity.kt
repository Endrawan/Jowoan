package com.example.jowoan.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import com.example.jowoan.adapters.*
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    private lateinit var rvNotifActivity: RecyclerView
    private var listActivity: ArrayList<AktifitasTeman> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)


        rvNotifActivity = findViewById(R.id.rv_notif_activity)

        listActivity.addAll(dataActivityTeman.listDataActivity)
        val greetingFriendAdapter = GreetingFriendAdapter(listActivity)
        rvNotifActivity.adapter = greetingFriendAdapter






    }
}