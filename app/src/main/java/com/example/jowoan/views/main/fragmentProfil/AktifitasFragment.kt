package com.example.jowoan.views.main.fragmentProfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jowoan.R.layout.fragment_aktifitas
import com.example.jowoan.config.LevelConfig
import com.example.jowoan.models.Level
import com.example.jowoan.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_aktifitas.*

class AktifitasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(fragment_aktifitas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserLevel()
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

        val progress = ((100 / currentLevel.completionNeeded) * completionsGained).toFloat()
        completionProgress.progress = progress
        completionProgress.progressText = "${progress.toInt()}%"
    }

}
