package com.example.jowoan.pengaturan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jowoan.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PengaturanActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_cliend_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun signOut() {
        Firebase.auth.signOut()
        googleSignInClient.signOut()
    }
}