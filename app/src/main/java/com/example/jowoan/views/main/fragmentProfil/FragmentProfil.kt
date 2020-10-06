package com.example.jowoan.views.main.fragmentProfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.jowoan.R
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.custom.Fragment
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.databinding.FragmentProfilBinding
import com.example.jowoan.views.main.MainActivity
import com.example.jowoan.views.pengaturan.PengaturanActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.fragment_profil.*


class FragmentProfil : Fragment() {

    private lateinit var act: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentProfilBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profil, container, false
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateProfile()
        setUpViewPager(ViewPagerProfil)
        tabLayoutProfil.setupWithViewPager(ViewPagerProfil)
        tabLayoutProfil.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        btn_pengaturan.setOnClickListener {
            startActivityForResult(
                Intent(act, PengaturanActivity::class.java),
                act.SETTINGS_REQUEST
            )
        }
    }

    private fun updateProfile() {
        textView_fullName.text = activity.user.fullName
        tv_poin_user.text = "${activity.user.points} Poin"

        GlideApp.with(activity).load("http://${activity.user.avatar?.URL}")
            .placeholder(ImageConfig.defaultAvatar).centerCrop()
            .into(imageView_avatar)
    }

    private fun setUpViewPager(viewPager: ViewPager) {
        val adapter = SectionPageAdapter(childFragmentManager)
        adapter.addFragment(AktifitasFragment(), "Aktifitas")
        adapter.addFragment(TemanFragment(), "Teman")
        viewPager.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            act.SETTINGS_REQUEST -> {
                activity.loadUser()
                updateProfile()
            }
        }
    }

}
