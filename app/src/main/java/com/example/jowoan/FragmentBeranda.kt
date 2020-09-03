package com.example.jowoan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jowoan.adapters.PracticeAdapter
import com.example.jowoan.custom.Fragment
import com.example.jowoan.internal.Utils
import com.example.jowoan.models.Practice
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.view_progress.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        activity.jowoanService.practiceGetAll().enqueue(object : Callback<List<Practice>> {
            override fun onResponse(
                call: Call<List<Practice>>,
                response: Response<List<Practice>>
            ) {
                hideLoading()
                if (response.isSuccessful) {
                    val u = response.body()
                    if (u != null) {
                        practices.clear()
                        practices.addAll(u)
                        adapter.notifyDataSetChanged()
                        Utils.toast(activity, "Accessed!")
                    }
                } else {
                    Utils.toast(activity, "${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Practice>>, t: Throwable) {
                Utils.toast(activity, t.message.toString())
                hideLoading()
            }

        })
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
