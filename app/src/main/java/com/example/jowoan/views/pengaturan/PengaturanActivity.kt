package com.example.jowoan.views.pengaturan

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.jowoan.R
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.models.User
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import kotlinx.android.synthetic.main.view_progress.*

class PengaturanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)
    }

    fun updateBackend(fieldName: String) {
        showLoading("Mengubah $fieldName di Database...")
        jowoanService.userUpdate(user.token, user.ID, user)
            .enqueue(APICallback(object : APICallback.Action<User> {
                override fun responseSuccess(data: User) {
                    hideLoading()
                    toast("Berhasil mengubah $fieldName!")
                    saveUser(data)
                }

                override fun dataNotFound(message: String) {
                    hideLoading()
                    toast("Data not found!")
                }

                override fun responseFailed(status: String, message: String) {
                    hideLoading()
                    toast("Pendaftaran Gagal! Status: $status Message: $message")
                }

                override fun networkFailed(t: Throwable) {
                    hideLoading()
                    toast("Pendaftaran Gagal! ${t.message}")
                }

                override fun tokenExpired() {
                    hideLoading()
                    toast("Token telah expired, silahkan login ulang")
                    logout()
                    Intent(this@PengaturanActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                }

            }))
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