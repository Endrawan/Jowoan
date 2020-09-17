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
import com.example.jowoan.databinding.FragmentEmailBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_email.*
import kotlinx.android.synthetic.main.view_progress.*

class EmailFragment : Fragment() {

    private val TAG = "EmaiLFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentEmailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)

        binding.buttonBack.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_emailFragment_to_fragmentPengaturan2)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText_email.setText(activity.user.email)
        button_submit.setOnClickListener { validateAndUpdateEmail() }
    }

    private fun validateAndUpdateEmail() {
        val email = editText_email.text.toString().trim()
        if (email.isEmpty()) {
            activity.toast("Email tidak boleh kosong!")
            return
        }
        activity.user.email = email
        updateFirebase()
    }

    private fun updateFirebase() {
        showLoading("Mengubah email di Firebase...")
        val user = FirebaseAuth.getInstance().currentUser

        user!!.updateEmail(activity.user.email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                    val a = activity as PengaturanActivity
                    a.updateBackend("email")
                } else {
                    hideLoading()
                    Log.d(TAG, "Update email failed. error:${task.exception?.message}")
                    activity.toast("Gagal mengubah email. error:${task.exception?.message}")
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