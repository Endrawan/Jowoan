package com.example.jowoan.FragmentProfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.jowoan.R
import com.example.jowoan.databinding.FragmentProfilBinding
import com.example.jowoan.pengaturan.PengaturanActivity

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profil.*


class FragmentProfil : Fragment() {
    private lateinit var auth: FirebaseAuth

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
//
//        btn_logout.setOnClickListener(View.OnClickListener {
//            if (auth.getCurrentUser() != null) auth.signOut()
//            val intent = Intent(activity, LoginActivity::class.java)
//            startActivity(intent)
//        })


        // Inflate the layout for this fragment
        return binding.root
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
