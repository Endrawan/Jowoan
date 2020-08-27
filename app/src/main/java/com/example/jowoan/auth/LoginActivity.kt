package com.example.jowoan.auth

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.jowoan.MainActivity
import com.example.jowoan.R
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.internal.Utils
import com.example.jowoan.models.User
import com.example.jowoan.network.Repository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.view_progress.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val auth = FirebaseAuth.getInstance()
    private val jowoanService = Repository.create()

    private val RC_SIGN_IN = 1
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_cliend_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

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
            googleSignIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed ${e.message}", e)
                hideLoading()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        showLoading("Melakukan autentikasi ke firebase...")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val u = auth.currentUser!!
                    user.fullName = u.displayName!!
                    user.email = u.email!!
                    user.externalType = "GOOGLE"
                    user.externalID = u.uid

                    val isNew = task.result!!.additionalUserInfo!!.isNewUser
                    Log.d(TAG, "is user new?:${isNew}")
                    if (isNew) signUpTokenForBackend()
                    else loginTokenForBackend()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    hideLoading()
                }
            }
    }

    private fun googleSignIn() {
        showLoading("Melakukan autentikasi ke google...")
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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
                    if (u != null) {
                        user = u
                        updateUI(user)
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

    private fun loginTokenForBackend() {
        showLoading("Mengambil akun dari database...")
        jowoanService.tokenSignIn(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    hideLoading()
                    Utils.toast(this@LoginActivity, "Login berhasil!")

                    val u = response.body()
                    Log.d(TAG, u.toString())
                    if (u != null) {
                        user = u
                        updateUI(user)
                    }
                } else {
                    Log.d(TAG, user.toString())
                    Log.d(TAG, "${response.code()} ${response.message()}")
                    Log.d(TAG, "requestBody:${gson.toJson(user)}")
                    hideLoading()
                    Utils.toast(
                        this@LoginActivity,
                        "Login Gagal! Gagal mengambil ke database"
                    )
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                hideLoading()
                Utils.toast(this@LoginActivity, "Login Gagal! ${t.message}")
            }
        })
    }

    private fun signUpTokenForBackend() {
        showLoading("Menyimpan akun ke database...")
        jowoanService.tokenSignUp(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    hideLoading()
                    Utils.toast(this@LoginActivity, "Pendaftaran berhasil!")

                    val u = response.body()
                    Log.d(TAG, u.toString())
                    if (u != null) {
                        user = u
                        updateUI(user)
                    }
                } else {
                    Log.d(TAG, user.toString())
                    Log.d(TAG, "${response.code()} ${response.message()}")
                    hideLoading()
                    Utils.toast(
                        this@LoginActivity,
                        "Pendaftaran Gagal! Gagal menyimpan ke database"
                    )
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                hideLoading()
                Utils.toast(this@LoginActivity, "Pendaftaran Gagal! ${t.message}")
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

    private fun updateUI(newUser: User) {
        if (auth.currentUser != null) {
            Log.d(TAG, newUser.toString())
            saveUser(newUser)
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                finishAffinity()
            }
        }
    }
}

