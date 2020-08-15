package com.example.jowoan.pengaturan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.example.jowoan.auth.LoginActivity
import com.example.jowoan.databinding.FragmentPengaturanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_pengaturan.*


class FragmentPengaturan : Fragment() {

    private lateinit var auth: FirebaseAuth
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout.setOnClickListener {
            Firebase.auth.signOut()
            Intent(requireContext(),LoginActivity::class.java).also {
                startActivity(it)

            }
            Toast.makeText(context,"Anda Telah Keluar",Toast.LENGTH_LONG).show()
        }

    }



}