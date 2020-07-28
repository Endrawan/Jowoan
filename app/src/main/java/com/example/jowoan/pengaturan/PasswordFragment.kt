package com.example.jowoan.pengaturan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jowoan.R
import com.example.jowoan.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentPasswordBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_password, container, false)

        binding.buttonBack.setOnClickListener(

            Navigation.createNavigateOnClickListener(R.id.action_passwordFragment_to_fragmentPengaturan2)
        )

        return binding.root
    }

}