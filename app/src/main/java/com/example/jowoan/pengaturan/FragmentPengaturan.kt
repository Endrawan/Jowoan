package com.example.jowoan.pengaturan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.example.jowoan.databinding.FragmentPengaturanBinding


class FragmentPengaturan : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val bindingPengaturan : FragmentPengaturanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_pengaturan, container, false)

        bindingPengaturan.pengaturanNama.setOnClickListener(

                Navigation.createNavigateOnClickListener(R.id.action_fragmentPengaturan2_to_namaFragment)
        )
        bindingPengaturan.pengaturanEmail.setOnClickListener(

            Navigation.createNavigateOnClickListener(R.id.action_fragmentPengaturan2_to_emailFragment)
        )
        bindingPengaturan.pengaturanPassword.setOnClickListener(

            Navigation.createNavigateOnClickListener(R.id.action_fragmentPengaturan2_to_passwordFragment)
        )
        bindingPengaturan.pengaturanTelpon.setOnClickListener(

            Navigation.createNavigateOnClickListener(R.id.action_fragmentPengaturan2_to_telponFragment)
        )


        bindingPengaturan.buttonBack.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)

        }
        return bindingPengaturan.root
    }



}