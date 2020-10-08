package com.example.jowoan.views.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jowoan.R
import com.example.jowoan.adapters.FriendActivityAdapter
import com.example.jowoan.adapters.RequestFriendAdapter
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.models.Friendship
import com.example.jowoan.models.User
import com.example.jowoan.network.APICallback
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    private val friendRequests = mutableListOf<User>()
    private val friendActivities = mutableListOf<User>()
    private lateinit var friendRequestsAdapter: RequestFriendAdapter
    private lateinit var friendActivitiesAdapter: FriendActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        buttonBack.setOnClickListener { finish() }

        friendRequestsAdapter =
            RequestFriendAdapter(friendRequests, object : RequestFriendAdapter.Action {
                override fun accept(friend: User, position: Int) {
                    acceptFriendship(friend.ID)
                    friendRequests.removeAt(position)
                    friendRequestsAdapter.notifyItemRemoved(position)
                    friendActivities.add(friend)
                    friendActivitiesAdapter.notifyItemInserted(friendActivities.size - 1)
                }

                override fun reject(friend: User, position: Int) {
                    rejectFriendship(friend.ID)
                    friendRequests.removeAt(position)
                    friendRequestsAdapter.notifyItemRemoved(position)
                }
            })
        recyclerView_friendRequest.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = this@NotificationActivity.friendRequestsAdapter
        }
        getFriendRequests()

        friendActivitiesAdapter =
            FriendActivityAdapter(friendActivities, object : FriendActivityAdapter.Action {
                override fun celebrate(friend: User) {
                    toast("Ucapan selamat telah dikirim!")
                }
            })
        recyclerView_friendActivity.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = this@NotificationActivity.friendActivitiesAdapter
        }

    }

    private fun getFriendRequests() {
        jowoanService.friendGetAllRequests(user.token, user.ID)
            .enqueue(APICallback(object : APICallback.Action<List<User>> {
                override fun responseSuccess(data: List<User>) {
                    friendRequests.clear()
                    friendRequests.addAll(data)
                    friendRequestsAdapter.notifyDataSetChanged()
                }

                override fun dataNotFound(message: String) {
                    toast(message)
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request gagal. error:${t.message}")
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }

            }))
    }

    private fun getFriendActivity() {
        jowoanService.friendGetAll(user.token, user.ID)
            .enqueue(APICallback(object : APICallback.Action<List<User>> {
                override fun responseSuccess(data: List<User>) {
                    friendActivities.clear()
                    friendActivities.addAll(data)
                    friendActivitiesAdapter.notifyDataSetChanged()
                }

                override fun dataNotFound(message: String) {
                    toast(message)
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request gagal. error:${t.message}")
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }
            }))
    }

    private fun rejectFriendship(friendID: Int) {
        jowoanService.friendReject(user.token, Friendship(user.ID, friendID, ""))
            .enqueue(APICallback(object : APICallback.Action<Friendship> {
                override fun responseSuccess(data: Friendship) {
                    toast(data.result)
                }

                override fun dataNotFound(message: String) {
                    toast(message)
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request gagal. error:${t.message}")
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }

            }))
    }

    private fun acceptFriendship(friendID: Int) {
        jowoanService.friendAccept(user.token, Friendship(user.ID, friendID, ""))
            .enqueue(APICallback(object : APICallback.Action<Friendship> {
                override fun responseSuccess(data: Friendship) {
                    toast(data.result)
                }

                override fun dataNotFound(message: String) {
                    toast(message)
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request gagal. error:${t.message}")
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }

            }))
    }
}