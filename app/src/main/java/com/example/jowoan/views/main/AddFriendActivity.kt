package com.example.jowoan.views.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jowoan.R
import com.example.jowoan.adapters.AddFriendAdapter
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.internal.Utils
import com.example.jowoan.models.User
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.view_progress.*

class AddFriendActivity : AppCompatActivity() {

    private val friends = mutableListOf<User>()
    private lateinit var adapter: AddFriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)

        buttonBack.setOnClickListener {
            finish()
        }

        hideLoading()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AddFriendAdapter(friends)
        recyclerView.adapter = adapter

        searchBox.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                Utils.hideKeyboard(this@AddFriendActivity)
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchUser(searchBox.text.toString())
                    return true
                }
                return false
            }
        })

    }

    private fun searchUser(keyword: String) {
        if (keyword.trim().isEmpty()) {
            toast("Nama teman tidak boleh kosong!")
            return
        }
        hideNotes()
        showLoading("Sedang mencari teman mohon tunggu...")

        jowoanService.searchUserByName(user.token, keyword)
            .enqueue(APICallback(object : APICallback.Action<List<User>> {
                override fun responseSuccess(data: List<User>) {
                    hideLoading()
                    friends.clear()
                    friends.addAll(data)
                    adapter.notifyDataSetChanged()
                }

                override fun dataNotFound(message: String) {
                    hideLoading()
                    toast(message)
                }

                override fun responseFailed(status: String, message: String) {
                    hideLoading()
                    toast("Request gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    hideLoading()
                    toast("Request gagal. error:${t.message}")
                }

                override fun tokenExpired() {
                    hideLoading()
                    toast("Token telah expired, silahkan login ulang")
                    logout()
                    Intent(this@AddFriendActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                }

            }))
    }

    private fun hideNotes() {
        add_friend_note.visibility = View.GONE
    }

    private fun showLoading(message: String) {
        progressView?.visibility = View.VISIBLE
        progressBar?.visibility = View.VISIBLE
        progressMessage.text = message
    }

    private fun hideLoading() {
        progressBar?.visibility = View.INVISIBLE
        progressMessage.text = ""
        progressView?.visibility = View.GONE
    }
}