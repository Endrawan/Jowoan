package com.example.jowoan.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.jowoan.Intro.Intro
import com.example.jowoan.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var handler = Handler()
        handler.postDelayed({
            Intent(applicationContext, Intro::class.java).also {
                startActivity(it)
                finish()
            }
        },5000)
    }
}
