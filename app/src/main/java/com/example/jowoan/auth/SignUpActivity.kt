package com.example.jowoan.auth

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.internal.Utils
import com.example.jowoan.models.User
import com.example.jowoan.network.APICallback
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.view_progress.*


class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tvMasuk.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        tvMasuk.setOnClickListener {
            Intent(applicationContext, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        btnBuatAkun.setOnClickListener {
            if (validateForm()) emailSignUp()
        }
    }

    private fun emailSignUp() {
        showLoading("Melakukan pembuatan akun pada Firebase...")

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    storeUserToBackend()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    hideLoading()
                    val errorMessages = task.exception?.message
                    Utils.toast(this@SignUpActivity, "Gagal melakukan daftar! $errorMessages")
                }
            }
    }

    private fun validateForm(): Boolean {
        user.fullName = et_fullname.editText?.text.toString().trim()
        user.email = et_email.editText?.text.toString().trim()
        user.password = et_password.editText?.text.toString().trim()
        user.phone = et_phone.editText?.text.toString().trim()

        if (user.fullName.isEmpty()) {
            Utils.toast(this, "Nama tidak boleh kosong!")
            return false
        }

        if (user.email.isEmpty()) {
            Utils.toast(this, "Email tidak boleh kosong!")
            return false
        }

        if (user.password.isEmpty()) {
            Utils.toast(this, "Password tidak boleh kosong!")
            return false
        }

        if (user.phone.isEmpty()) {
            Utils.toast(this, "Nomer telepon tidak boleh kosong!")
            return false
        }

        return true
    }

    private fun storeUserToBackend() {
        showLoading("Menyimpan akun ke database...")
        jowoanService.emailSignUp(user).enqueue(APICallback(object : APICallback.Action<User> {
            override fun responseSuccess(data: User) {
                hideLoading()
                toast("Pendaftaran berhasil!")
                saveUser(data)
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    finishAffinity()
                }
            }

            override fun dataNotFound(message: String) {
                hideLoading()
                toast("Data not found!")
            }

            override fun responseFailed(status: String, message: String) {
                hideLoading()
                toast("Pendaftaran Gagal! Status: $status Message: $message")
            }

            override fun networkFailed(t: Throwable) {
                hideLoading()
                toast("Pendaftaran Gagal! ${t.message}")
            }
        }))
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