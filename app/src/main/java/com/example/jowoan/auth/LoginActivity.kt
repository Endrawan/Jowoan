package com.example.jowoan.auth

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tvLP.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tbBA.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        tbBA.setOnClickListener {
            Intent(applicationContext, SignUpActivity::class.java).also {
                startActivity(it)

            }
        }

        btnLogin.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
