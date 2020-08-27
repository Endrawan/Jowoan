package com.example.jowoan

import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jowoan.FragmentProfil.FragmentProfil
import com.example.jowoan.FragmentToko.FragmentToko
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
//        val navCtrl = this.findNavController(R.id.navhost_fragment)

        val fragmentBeranda = FragmentBeranda()
        val fragmentProfil = FragmentProfil()
        val fragmentShop = FragmentToko()
        setFragment(fragmentBeranda)

        iv_home.setOnClickListener {
            setFragment(fragmentBeranda)

            changeIcon(iv_home,R.drawable.ic_home_active)
            changeIcon(iv_shop,R.drawable.ic_shop_inactive)
            changeIcon(iv_profil,R.drawable.ic_profil_inactive)
        }
        iv_profil.setOnClickListener {
            setFragment(fragmentProfil)

            changeIcon(iv_home,R.drawable.ic_home_inactive)
            changeIcon(iv_shop,R.drawable.ic_shop_inactive)
            changeIcon(iv_profil,R.drawable.ic_profil_active)
        }
        iv_shop.setOnClickListener {
            setFragment(fragmentShop)

            changeIcon(iv_home,R.drawable.ic_home_inactive)
            changeIcon(iv_shop,R.drawable.ic_shop_active)
            changeIcon(iv_profil,R.drawable.ic_profil_inactive)
        }



    }

    private fun setFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutFragment,fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView : ImageView, int : Int){
        imageView.setImageResource(int)
    }
}
