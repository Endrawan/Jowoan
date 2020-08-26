package com.example.jowoan.auth

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.example.jowoan.internal.Utils
import com.example.jowoan.models.User
import com.example.jowoan.network.Repository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.view_progress.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val auth = FirebaseAuth.getInstance()
    private var user = User()
    private val jowoanService = Repository.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvLP.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        tbBA.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        tbBA.setOnClickListener {
            Intent(applicationContext, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

        btnLogin.setOnClickListener {
            if (validateForm()) emailSignIn()
        }

        btn_login_google.setOnClickListener {

        }
    }

    private fun emailSignIn() {
        showLoading("Melakukan autentikasi ke firebase...")

        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    loginEmailForBackend()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    hideLoading()
                    val errorMessages = task.exception?.message
                    Utils.toast(this@LoginActivity, "Gagal melakukan login! $errorMessages")
                }
            }
    }

    private fun loginEmailForBackend() {
        showLoading("Mengambil akun dari database...")
        jowoanService.emailSignIn(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    hideLoading()
                    Utils.toast(this@LoginActivity, "Login berhasil!")

                    val u = response.body()
                    Log.d(TAG, u.toString())
                    if (u != null) user = u

                    Intent(applicationContext, MainActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                } else {
                    Log.d(TAG, user.toString())
                    Log.d(TAG, "${response.code()} ${response.message()}")
                    hideLoading()
                    Utils.toast(
                        this@LoginActivity,
                        "Login Gagal! Gagal menyimpan ke database"
                    )
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                hideLoading()
                Utils.toast(this@LoginActivity, "Login Gagal! ${t.message}")
            }
        })
    }

    private fun validateForm(): Boolean {
        user.email = et_email_login.editText?.text.toString().trim()
        user.password = et_password_login.editText?.text.toString().trim()

        if (user.email.isEmpty()) {
            Utils.toast(this, "Email tidak boleh kosong!")
            return false
        }

        if (user.password.isEmpty()) {
            Utils.toast(this, "Password tidak boleh kosong!")
            return false
        }
        return true
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

