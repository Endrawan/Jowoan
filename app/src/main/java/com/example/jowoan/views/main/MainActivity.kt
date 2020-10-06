package com.example.jowoan.views.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.jowoan.R
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.databinding.ActivityMainBinding
import com.example.jowoan.models.*
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import com.example.jowoan.views.main.fragmentProfil.FragmentProfil
import com.example.jowoan.views.main.fragmentToko.FragmentToko
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val completions = mutableListOf<Completion>()
    val activities = mutableListOf<Activity>()
    val practices = mutableListOf<Practice>()
    val avatars = mutableListOf<Avatar>()
    val fragmentBeranda = FragmentBeranda()
    val fragmentProfil = FragmentProfil()
    val fragmentShop = FragmentToko()
    val completionsRequestStatus = MutableLiveData<Boolean>(false)
    val activitiesRequestStatus = MutableLiveData<Boolean>(false)
    val practicesRequestStatus = MutableLiveData<Boolean>(false)
    val avatarsRequestStatus = MutableLiveData<Boolean>(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
//        val navCtrl = this.findNavController(R.id.navhost_fragment)

        setFragment(fragmentBeranda)

        loadCompletions()
        loadActivities()
        loadPractice()
        loadAvatars()

        iv_home.setOnClickListener {
            setFragment(fragmentBeranda)

            changeIcon(iv_home, R.drawable.ic_home_active)
            changeIcon(iv_shop, R.drawable.ic_shop_inactive)
            changeIcon(
                iv_profil,
                R.drawable.ic_profil_inactive
            )
        }
        iv_profil.setOnClickListener {
            setFragment(fragmentProfil)

            changeIcon(iv_home, R.drawable.ic_home_inactive)
            changeIcon(iv_shop, R.drawable.ic_shop_inactive)
            changeIcon(iv_profil, R.drawable.ic_profil_active)
        }
        iv_shop.setOnClickListener {
            setFragment(fragmentShop)

            changeIcon(iv_home, R.drawable.ic_home_inactive)
            changeIcon(iv_shop, R.drawable.ic_shop_active)
            changeIcon(
                iv_profil,
                R.drawable.ic_profil_inactive
            )
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutFragment, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    fun loadCompletions() {
        jowoanService.completionGetAll(user.token, user.ID)
            .enqueue(APICallback(object : APICallback.Action<List<Completion>> {
                override fun responseSuccess(data: List<Completion>) {
                    completions.clear()
                    completions.addAll(data)
                    completionsRequestStatus.value = true
                    syncSubpracticeWithCompletion()
                }

                override fun dataNotFound(message: String) {
                    toast("Completions data not found!")
                    completionsRequestStatus.value = true
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request completions gagal. status:$status, message:$message")
                    completionsRequestStatus.value = true
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request completions gagal. error:${t.message}")
                    completionsRequestStatus.value = true
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }
            }))
    }

    fun loadActivities() {
        jowoanService.activityGetAll(user.token, user.ID)
            .enqueue(APICallback(object : APICallback.Action<List<Activity>> {
                override fun responseSuccess(data: List<Activity>) {
                    activities.clear()
                    activities.addAll(data)
                    activitiesRequestStatus.value = true
                }

                override fun dataNotFound(message: String) {
                    toast(message)
                    activitiesRequestStatus.value = true
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request activities gagal. status:$status, message:$message")
                    activitiesRequestStatus.value = true
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request activities gagal. error:${t.message}")
                    activitiesRequestStatus.value = true
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }

            }))
    }

    fun loadPractice() {
        jowoanService.practiceGetAll(user.token)
            .enqueue(APICallback(object : APICallback.Action<List<Practice>> {
                override fun responseSuccess(data: List<Practice>) {
                    practices.clear()
                    practices.addAll(data)
                    practicesRequestStatus.value = true
                    syncSubpracticeWithCompletion()
                }

                override fun dataNotFound(message: String) {
                    toast(message)
                    practicesRequestStatus.value = true
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request gagal. status:$status, message:$message")
                    practicesRequestStatus.value = true
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request gagal. error:${t.message}")
                    practicesRequestStatus.value = true
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }
            }))
    }

    private fun loadAvatars() {
        jowoanService.avatarGetAll(user.token)
            .enqueue(APICallback(object : APICallback.Action<List<Avatar>> {
                override fun responseSuccess(data: List<Avatar>) {
                    avatars.clear()
                    avatars.addAll(data)
                    avatarsRequestStatus.value = true
                }

                override fun dataNotFound(message: String) {
                    toast(message)
                    avatarsRequestStatus.value = true
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request gagal. status:$status, message:$message")
                    avatarsRequestStatus.value = true
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request gagal. error:${t.message}")
                    avatarsRequestStatus.value = true
                }

                override fun tokenExpired() {
                    handleTokenExpired()
                }

            }))
    }

    fun refreshUser() {
        jowoanService.userGet(user.token, user.ID)
            .enqueue(APICallback(object : APICallback.Action<User> {
                override fun responseSuccess(data: User) {
                    saveUser(data)
                }

                override fun dataNotFound(message: String) {
                }

                override fun responseFailed(status: String, message: String) {
                }

                override fun networkFailed(t: Throwable) {
                }

                override fun tokenExpired() {
                    toast("Token telah expired, silahkan login ulang")
                    logout()
                    Intent(this@MainActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                }

            }))
    }

    fun syncSubpracticeWithCompletion() {
        if (!completionsRequestStatus.value!! || !practicesRequestStatus.value!!) return
        for (practice in practices) {
            for (subpractice in practice.subpractices) {
                for (completion in completions) {
                    if (subpractice.ID == completion.subpracticeID) {
                        subpractice.completionStatus = true
                        break
                    }
                }
            }
        }
        fragmentBeranda.requestsDone.value = true
    }

    private fun handleTokenExpired() {
        toast("Token telah expired, silahkan login ulang")
        logout()
        Intent(this@MainActivity, LoginActivity::class.java).also {
            startActivity(it)
            finishAffinity()
        }
    }
}
