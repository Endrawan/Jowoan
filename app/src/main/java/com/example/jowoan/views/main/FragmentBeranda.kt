package com.example.jowoan.views.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jowoan.R
import com.example.jowoan.adapters.PracticeAdapter
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.config.LevelConfig
import com.example.jowoan.custom.Fragment
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.Level
import com.example.jowoan.models.Subpractice
import com.example.jowoan.views.lesson.LessonActivity
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.view_progress.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentBeranda : Fragment() {

    private lateinit var adapter: PracticeAdapter
    private lateinit var act: MainActivity

    val requestsDone: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity as MainActivity
        adapter = PracticeAdapter(act.practices, object : PracticeAdapter.Action {
            override fun subpracticeClicked(subpractice: Subpractice) {
                val intent = Intent(activity, LessonActivity::class.java)
                intent.putExtra("SubpracticeID", subpractice.ID)
                startActivity(intent)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_beranda, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading("Mengambil data practice...")
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@FragmentBeranda.adapter
        }
        setUserLevel()
        requestsDone.observe(act, Observer {
            if (it) {
                hideLoading()
                adapter.notifyDataSetChanged()
                setUserLevel()
            }
        })
    }

    private fun setUserLevel() {
        textView_fullName.text = activity.user.fullName
        GlideApp.with(activity).load("http://${activity.user.avatar?.URL}")
            .placeholder(ImageConfig.defaultAvatar).centerCrop()
            .into(imageView)

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
