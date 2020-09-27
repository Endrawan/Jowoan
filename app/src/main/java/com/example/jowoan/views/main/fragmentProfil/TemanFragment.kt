package com.example.jowoan.views.main.fragmentProfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jowoan.R
import com.example.jowoan.views.main.AddFriendActivity
import kotlinx.android.synthetic.main.fragment_teman.*

//
class TemanFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teman, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFriend.setOnClickListener {
            Intent(context, AddFriendActivity::class.java).also {
                startActivity(it)

            }

        }


    }
    }
