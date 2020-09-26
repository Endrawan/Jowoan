package com.example.jowoan.views.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jowoan.R
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.databinding.ActivityMainBinding
import com.example.jowoan.models.Completion
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import com.example.jowoan.views.main.fragmentProfil.FragmentProfil
import com.example.jowoan.views.main.fragmentToko.FragmentToko
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val completions = mutableListOf<Completion>()
    val fragmentBeranda = FragmentBeranda()
    val fragmentProfil = FragmentProfil()
    val fragmentShop = FragmentToko()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
//        val navCtrl = this.findNavController(R.id.navhost_fragment)

        loadCompletions()
        setFragment(fragmentBeranda)

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
                    fragmentBeranda.syncSubpracticeWithCompletion()
                }

                override fun dataNotFound(message: String) {
                    toast("Completions data not found!")
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request completions gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request completions gagal. error:${t.message}")
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
}
