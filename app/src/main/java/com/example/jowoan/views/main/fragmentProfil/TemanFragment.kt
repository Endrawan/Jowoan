package com.example.jowoan.views.main.fragmentProfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jowoan.R
import com.example.jowoan.custom.Fragment
import com.example.jowoan.views.main.AddFriendActivity
import com.example.jowoan.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_teman.*

class TemanFragment : Fragment() {

    private lateinit var act: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_teman, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFriend.setOnClickListener {
            startActivityForResult(
                Intent(activity, AddFriendActivity::class.java),
                act.ADD_FRIEND_REQUEST
            )
            Intent(context, AddFriendActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
