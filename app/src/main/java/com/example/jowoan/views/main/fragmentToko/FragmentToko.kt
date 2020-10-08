package com.example.jowoan.views.main.fragmentToko

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jowoan.R
import com.example.jowoan.adapters.AvatarAdapter
import com.example.jowoan.custom.Fragment
import com.example.jowoan.models.Avatar
import com.example.jowoan.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_toko.*
import kotlinx.android.synthetic.main.view_progress.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentToko : Fragment() {

    val DIALOG_REQUEST_CODE = 100;
    private lateinit var adapter: AvatarAdapter
    private lateinit var act: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        act = activity as MainActivity
        adapter = AvatarAdapter(act.avatars, object : AvatarAdapter.Action {
            override fun clicked(avatar: Avatar) {
                val fm = activity.supportFragmentManager
                val dialogFragment = TokoDialogFragment.newInstance(avatar)
                dialogFragment.setTargetFragment(this@FragmentToko, DIALOG_REQUEST_CODE)
                dialogFragment.show(fm, "fragment_avatar_detail")
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_toko, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading("Mengambil data avatar...")
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = this@FragmentToko.adapter
        }
        poinUser.text = "${activity.user.points} Poin"
        act.avatarsRequestStatus.observe(act, Observer {
            if (it && isVisible) {
                hideLoading()
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DIALOG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            poinUser.text = "${activity.user.points} Poin"
        }
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
