package com.example.jowoan.auth

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.example.jowoan.internal.Utils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.view_progress.*

const val REQUEST_CODE_SIGN_IN = 0

class LoginActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null

    //Handler
    var handler = Handler()

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //ProgressBar
        progressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar

        //auth
        auth = FirebaseAuth.getInstance()

        tvLP.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
        tbBA.paintFlags = Paint.UNDERLINE_TEXT_FLAG;

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
        val email: String = et_email_login.editText?.text.toString().trim()
        val password: String = et_password_login.editText?.text.toString().trim()

        showLoading("Melakukan autentikasi ke firebase...")

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Succes", "createUserWithEmail:success")
                    Utils.toast(this@LoginActivity, "Sign in berhasil!")
                    hideLoading()
                    Intent(applicationContext, MainActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                } else {
                    Log.w("Fail", "createUserWithEmail:failure", task.exception)
                    hideLoading()
                    val errorMessages = task.exception?.message
                    Utils.toast(this@LoginActivity, "Gagal melakukan login! $errorMessages")
                }
            }
    }

    private fun validateForm(): Boolean {
        val email: String = et_email_login.editText?.text.toString().trim()
        val password: String = et_password_login.editText?.text.toString().trim()
        if (email.isEmpty() || password.isEmpty()) {
            Utils.toast(this, "Email dan Password tidak boleh kosong!")
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

