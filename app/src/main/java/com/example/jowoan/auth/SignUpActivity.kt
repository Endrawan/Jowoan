package com.example.jowoan.auth

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        tvMasuk.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        tvMasuk.setOnClickListener {
            Intent(applicationContext, LoginActivity::class.java).also {
                startActivity(it)

            }
        }

        btnBuatAkun.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}