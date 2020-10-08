package com.example.jowoan.views.main.fragmentProfil

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.jowoan.R
import com.example.jowoan.R.layout.fragment_aktifitas
import com.example.jowoan.config.LevelConfig
import com.example.jowoan.custom.Fragment
import com.example.jowoan.models.Level
import com.example.jowoan.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_aktifitas.*
import kotlinx.android.synthetic.main.view_streaks.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.TemporalAdjusters.previousOrSame


class AktifitasFragment : Fragment() {

    private lateinit var act: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(fragment_aktifitas, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        act.completionsRequestStatus.observe(act, Observer {
            if (it && isVisible) {
                setUserLevel()
                setStreaksView()
            }
        })
    }

    private fun setUserLevel() {
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

    private fun setStreaksView() {
        val today: LocalDate = LocalDate.now()
        val monday: LocalDate = today.with(previousOrSame(DayOfWeek.MONDAY))

        val act = activity as MainActivity
        var lastDayActive: DayOfWeek? = null
        var streaks = 0
        for (userAct in act.activities) {
            if (userAct.createdAt == null) break
            val date: LocalDate =
                Instant.ofEpochMilli(userAct.createdAt.time).atZone(ZoneId.systemDefault())
                    .toLocalDate()
            if (monday.isBefore(date)) {
                setDayActive(date.dayOfWeek)
                if (lastDayActive != date.dayOfWeek) {
                    streaks++
                    lastDayActive = date.dayOfWeek
                }
            }
        }
        textView_totalStreaks.text = "$streaks Hari aktif"
    }

    private fun setDayActive(day: DayOfWeek) {
        when (day) {
            DayOfWeek.MONDAY -> setIndicator(imageView_mondayIndicator, true)
            DayOfWeek.TUESDAY -> setIndicator(imageView_tuesdayIndicator, true)
            DayOfWeek.WEDNESDAY -> setIndicator(imageView_wednesdayIndicator, true)
            DayOfWeek.THURSDAY -> setIndicator(imageView_thursdayIndicator, true)
            DayOfWeek.FRIDAY -> setIndicator(imageView_fridayIndicator, true)
            DayOfWeek.SATURDAY -> setIndicator(imageView_saturdayIndicator, true)
            DayOfWeek.SUNDAY -> setIndicator(imageView_sundayIndicator, true)
        }
    }

    private fun setIndicator(indicator: ImageView, isActive: Boolean) {
        val image: Drawable? = if (isActive) {
            ContextCompat.getDrawable(activity, R.drawable.hadir_hari)
        } else {
            ContextCompat.getDrawable(activity, R.drawable.tidak_hadir_hari)
        }
        indicator.setImageDrawable(image)
    }

}
