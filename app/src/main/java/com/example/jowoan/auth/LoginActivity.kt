package com.example.jowoan.auth

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE
import com.example.jowoan.Intro.Intro
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.core.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.*
import java.lang.Exception

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

        tvLP.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tbBA.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        tbBA.setOnClickListener {
            Intent(applicationContext, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
        btnLogin.setOnClickListener {

            val email: String = et_email_login.getEditText()?.getText().toString().trim()
            val password: String = et_password_login.getEditText()?.getText().toString().trim()
            if (email.isEmpty() && password.isEmpty()) {
                Snackbar.make(it, "Email dan Password Tidak boleh Kosong !", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                signIn()
            }
        }

        btn_login_google.setOnClickListener {

        }
    }

    private fun signIn() {
        val email: String = et_email_login.getEditText()?.getText().toString().trim()
        val password: String = et_password_login.getEditText()?.getText().toString().trim()

        loading()
        auth.signInWithEmailAndPassword(email, password)
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

