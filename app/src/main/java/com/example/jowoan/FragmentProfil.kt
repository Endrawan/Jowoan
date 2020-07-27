package com.example.jowoan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jowoan.databinding.FragmentProfilBinding
import com.google.firebase.auth.FirebaseAuth


class Profil : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentProfilBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_profil, container, false)
        binding.btnLogout.setOnClickListener{
            


        }


//
//        btn_logout.setOnClickListener(View.OnClickListener {
//            if (auth.getCurrentUser() != null) auth.signOut()
//            val intent = Intent(activity, LoginActivity::class.java)
//            startActivity(intent)
//        })

        // Inflate the layout for this fragment
        return binding.root
    }

}
