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
import com.example.jowoan.views.pengaturan.PengaturanActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.fragment_profil.*


class FragmentProfil : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentProfilBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profil, container, false
        )

        binding.btnPengaturan.setOnClickListener {
            val intent = Intent(requireContext(), PengaturanActivity::class.java)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        textView_fullName.text = activity.user.fullName
        tv_poin_user.text = "${activity.user.points} Poin"

        GlideApp.with(activity).load("http://${activity.user.avatar?.URL}")
            .placeholder(ImageConfig.defaultAvatar).centerCrop()
            .into(imageView_avatar)
    }

    //Call onActivity Create method
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpViewPager(ViewPagerProfil)

        //tablayout
        tabLayoutProfil.setupWithViewPager(ViewPagerProfil)
        tabLayoutProfil.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
    private fun setUpViewPager(viewPager: ViewPager) {

        val adapter = SectionPageAdapter(childFragmentManager)

        adapter.addFragment(AktifitasFragment(), "Aktifitas")
        adapter.addFragment(TemanFragment(), "Teman")

        viewPager.adapter = adapter
    }

}
