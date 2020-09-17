package com.example.jowoan.views.pengaturan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jowoan.R
import com.example.jowoan.custom.Fragment
import com.example.jowoan.databinding.FragmentNamaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_nama.*
import kotlinx.android.synthetic.main.view_progress.*

class NamaFragment : Fragment() {

    private val TAG = "NamaFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentNamaBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_nama, container, false)

        binding.buttonBack.setOnClickListener(

            Navigation.createNavigateOnClickListener(R.id.action_namaFragment_to_fragmentPengaturan2)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText_name.setText(activity.user.fullName)
        button_submit.setOnClickListener { validateAndUpdateName() }
    }

    private fun validateAndUpdateName() {
        val name = editText_name.text.toString().trim()
        if (name.length < 6) {
            activity.toast("Nama tidak boleh kurang dari 6 karakter!")
            return
        }
        activity.user.fullName = name
        updateFirebase()
    }

    private fun updateFirebase() {
        showLoading("Mengubah nama di Firebase...")
        val user = FirebaseAuth.getInstance().currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = activity.user.fullName
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User name updated.")
                    val a = activity as PengaturanActivity
                    a.updateBackend("nama")
                } else {
                    hideLoading()
                    Log.d(TAG, "Update nama failed. error:${task.exception?.message}")
                    activity.toast("Gagal mengubah nama. error:${task.exception?.message}")
                }
            }
    }

    private fun showLoading(message: String) {
        progressBar?.visibility = View.VISIBLE
        progressMessage.text = message
    }

    private fun hideLoading() {
        progressBar?.visibility = View.INVISIBLE
        progressMessage.text = ""
    }

}