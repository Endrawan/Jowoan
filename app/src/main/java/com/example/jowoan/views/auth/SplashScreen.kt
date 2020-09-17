package com.example.jowoan.views.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.jowoan.R
import com.example.jowoan.views.intro.Intro
import com.example.jowoan.views.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        auth = FirebaseAuth.getInstance()

        val handler = Handler()
        handler.postDelayed({
            if(auth.currentUser==null){
                Intent(applicationContext, Intro::class.java).also {
                    startActivity(it)
                    finishAffinity()
                }
            } else {
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    finishAffinity()
                }
            }

        },2000)
    }
}
