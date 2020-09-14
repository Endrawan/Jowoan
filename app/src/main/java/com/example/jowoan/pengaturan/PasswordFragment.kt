package com.example.jowoan.pengaturan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jowoan.R
import com.example.jowoan.custom.Fragment
import com.example.jowoan.databinding.FragmentPasswordBinding
import com.example.jowoan.internal.HashUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.view_progress.*


class PasswordFragment : Fragment() {

    private val TAG = "PasswordFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPasswordBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)

        binding.buttonBack.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_passwordFragment_to_fragmentPengaturan2)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_submit.setOnClickListener { validateAndUpdatePassword() }
    }

    private fun validateAndUpdatePassword() {
        val oldPassword = HashUtils.sha1(editText_oldPassword.text.toString().trim())
        val newPassword = editText_newPassword.text.toString().trim()
        val newPasswordConfirm = editText_newPasswordConfirm.text.toString().trim()

        if (oldPassword != activity.user.password) {
            activity.toast("Password lama tidak benar.")
            return
        }
        if (newPassword.length < 6) {
            activity.toast("Password baru tidak boleh kurang dari 6 karakter")
            return
        }
        if (newPassword != newPasswordConfirm) {
            activity.toast("Password baru tidak sama dengan konfirmasi password")
            return
        }
        activity.user.password = newPassword
        updateFirebase()
    }

    private fun updateFirebase() {
        showLoading("Mengubah password di Firebase...")
        val user = FirebaseAuth.getInstance().currentUser

        user!!.updatePassword(activity.user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                    val a = activity as PengaturanActivity
                    a.updateBackend("password")
                } else {
                    hideLoading()
                    Log.d(TAG, "Update password failed. error:${task.exception?.message}")
                    activity.toast("Gagal mengubah password. error:${task.exception?.message}")
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