package com.example.jowoan.custom

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jowoan.R
import com.example.jowoan.internal.Utils
import com.example.jowoan.models.User
import com.example.jowoan.network.Repository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

open class AppCompatActivity : AppCompatActivity() {
    val gson = Gson()
    var user: User = User()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val TAG = "AppCompatActivity"

    val jowoanService = Repository.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadUser()
        checkUser()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_cliend_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun checkUser() {
        if (user.fullName.isNotEmpty()) {
            Utils.toast(this, "User logged in! ${user.fullName}")
        } else {
            Utils.toast(this, "User not logged in!")
        }
    }

    fun saveUser(user: User) {
        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.saved_user), gson.toJson(user))
            apply()
        }
        this.user = user
    }

    fun loadUser() {
        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        val userJSON = sharedPref.getString(getString(R.string.saved_user), null)
        if (userJSON != null) user = gson.fromJson(userJSON, User::class.java)
        else user = User()
        Log.d(TAG, userJSON.toString())
    }

    fun logout() {
        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        with(sharedPref.edit()) {
            remove(getString(R.string.saved_user))
            apply()
        }
        Firebase.auth.signOut()
        googleSignInClient.signOut()
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}