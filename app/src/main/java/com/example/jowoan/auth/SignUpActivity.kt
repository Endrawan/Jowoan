package com.example.jowoan.auth

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jowoan.Intro.Intro
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        tvMasuk.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


        tvMasuk.setOnClickListener {
            Intent(applicationContext, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        btnBuatAkun.setOnClickListener {
            val email: String = et_email.getEditText()?.getText().toString().trim()
            val password: String = et_password.getEditText()?.getText().toString().trim()
            if (email.isEmpty() && password.isEmpty()) {
                Snackbar.make(it, "Field Tidak Boleh Kosong", Snackbar.LENGTH_LONG)
                    .show()
            }
            signUp()
            loading()
        }
    }

    private fun signUp() {
        val email: String = et_email.getEditText()?.getText().toString().trim()
        val password: String = et_password.getEditText()?.getText().toString().trim()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Succes", "createUserWithEmail:success")
                    Intent(applicationContext, MainActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Fail", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

    }

    private fun loading() {
        //Bar ki dihapus gara2 ternyata progressDialog udh di desprate ket API 26 kalau user salah masukin ntar looping teros
        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Harap Menunggu Sebentar")
        progressDialog.show()
        var handler = Handler()
        handler.postDelayed({

        }, 3000)

    }
}