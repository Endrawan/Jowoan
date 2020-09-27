package com.example.jowoan.views.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jowoan.R
import com.example.jowoan.adapters.PracticeAdapter
import com.example.jowoan.config.LevelConfig
import com.example.jowoan.custom.Fragment
import com.example.jowoan.models.Level
import com.example.jowoan.models.Practice
import com.example.jowoan.models.Subpractice
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import com.example.jowoan.views.lesson.LessonActivity
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
        adapter = PracticeAdapter(practices, object : PracticeAdapter.Action {

            override fun subpracticeClicked(subpractice: Subpractice) {
                val intent = Intent(activity, LessonActivity::class.java)
                intent.putExtra("SubpracticeID", subpractice.ID)
                startActivity(intent)
            }

        })
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
                    syncSubpracticeWithCompletion()
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

    fun syncSubpracticeWithCompletion() {
        val act = activity as MainActivity
        if (act.completions.size == 0) return
        if (practices.size == 0) return
        for (practice in practices) {
            for (subpractice in practice.subpractices) {
                for (completion in act.completions) {
                    if (subpractice.ID == completion.subpracticeID) {
                        subpractice.completionStatus = true
                        break
                    }
                }
            }
        }
        setUserLevel()
        adapter.notifyDataSetChanged()
    }

    fun setUserLevel() {
        val act = activity as MainActivity
        var completionsGained = act.completions.size
        var idx = 0
        var currentLevel: Level = LevelConfig.levels[idx]

        while (completionsGained >= LevelConfig.levels[idx].completionNeeded) {
            completionsGained -= LevelConfig.levels[idx].completionNeeded
            idx++
            currentLevel = LevelConfig.levels[idx]
        }

        textView_userCompletions.text = completionsGained.toString()
        textView_completionsNeeded.text = "/${currentLevel.completionNeeded}"
        textView_tingkatLevel.text = "Tingkat ${currentLevel.name}"
        textView_level.text = currentLevel.name

        val progress = ((100 / currentLevel.completionNeeded) * completionsGained).toFloat()
        completionProgress.progress = progress
        completionProgress.progressText = "${progress.toInt()}%"
    }

    private fun showLoading(message: String) {
        progressBar?.visibility = View.VISIBLE
        progressMessage.text = message
    }

    private fun hideLoading() {
        progressBar?.visibility = View.INVISIBLE
        progressMessage.text = ""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_lonceng.setOnClickListener {
            Intent(context, NotificationActivity::class.java).also {
                startActivity(it)
            }

        }
    }
}
