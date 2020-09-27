package com.example.jowoan.views.main.fragmentToko

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.jowoan.R
import com.example.jowoan.adapters.AvatarAdapter
import com.example.jowoan.custom.Fragment
import com.example.jowoan.models.Avatar
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_toko.*
import kotlinx.android.synthetic.main.view_progress.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentToko : Fragment() {

    val avatars = mutableListOf<Avatar>()
    private lateinit var adapter: AvatarAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = AvatarAdapter(avatars, object : AvatarAdapter.Action {
            override fun clicked(avatar: Avatar) {
                val fm = activity.supportFragmentManager
                val dialogFragment = TokoDialogFragment.newInstance(avatar)
                dialogFragment.show(fm, "fragment_avatar_detail")
            }

        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toko, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading("Mengambil data avatar...")
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = this@FragmentToko.adapter
        }
        loadAvatars()
    }

    override fun onResume() {
        super.onResume()
        poinUser.text = "${activity.user.points} Poin"
    }

    private fun showDialogFilter() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.fragment_dialog)
        dialog.show()
    }

    private fun loadAvatars() {
        activity.jowoanService.avatarGetAll(activity.user.token)
            .enqueue(APICallback(object : APICallback.Action<List<Avatar>> {
                override fun responseSuccess(data: List<Avatar>) {
                    hideLoading()
                    avatars.clear()
                    avatars.addAll(data)
                    adapter.notifyDataSetChanged()
                }

                override fun dataNotFound(message: String) {
                    hideLoading()
                    activity.toast(message)
                }

                override fun responseFailed(status: String, message: String) {
                    hideLoading()
                    activity.toast("Request gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    hideLoading()
                    activity.toast("Request gagal. error:${t.message}")
                }

                override fun tokenExpired() {
                    hideLoading()
                    activity.toast("Token telah expired, silahkan login ulang")
                    activity.logout()
                    Intent(requireContext(), LoginActivity::class.java).also {
                        startActivity(it)
                        activity.finishAffinity()
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
