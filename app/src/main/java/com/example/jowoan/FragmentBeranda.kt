package com.example.jowoan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jowoan.adapters.PracticeAdapter
import com.example.jowoan.auth.LoginActivity
import com.example.jowoan.custom.Fragment
import com.example.jowoan.models.Practice
import com.example.jowoan.network.APICallback
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.view_progress.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentBeranda : Fragment() {

    var practices = mutableListOf<Practice>()
    private lateinit var adapter: PracticeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = PracticeAdapter(practices)
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading("Mengambil data practice...")
        textView_fullName.text = activity.user.fullName
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@FragmentBeranda.adapter
        }
        loadPractice()
    }

    private fun loadPractice() {
        activity.jowoanService.practiceGetAll(activity.user.token)
            .enqueue(APICallback(object : APICallback.Action<List<Practice>> {
                override fun responseSuccess(data: List<Practice>) {
                    hideLoading()
                    practices.clear()
                    practices.addAll(data)
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
