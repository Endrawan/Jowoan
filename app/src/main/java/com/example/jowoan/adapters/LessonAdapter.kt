package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R.layout.*
import com.example.jowoan.config.LessonConfig
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.models.lesson.Lesson
import com.example.jowoan.viewholders.*


class LessonAdapter(
    private val lessons: MutableList<Lesson>,
    val activity: AppCompatActivity,
    val action: Action
) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    val lessonActions = mutableMapOf<Int, LessonAction>()

    override fun getItemCount(): Int = lessons.size

    override fun getItemViewType(position: Int): Int = lessons[position].type

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LessonViewHolder {
        return when (viewType) {
            LessonConfig.PENJELASAN_TYPE -> PenjelasanViewHolder(
                LayoutInflater.from(parent.context).inflate(item_penjelasan, parent, false),
                action
            )
            LessonConfig.TIPS_TYPE -> TipsViewHolder(
                LayoutInflater.from(parent.context).inflate(item_tips, parent, false),
                action
            )
            LessonConfig.HAFALAN_TYPE -> HafalanViewHolder(
                LayoutInflater.from(parent.context).inflate(item_hafalan, parent, false),
                action
            )
            LessonConfig.PILIH_KATA_TYPE -> PilihKataViewHolder(
                LayoutInflater.from(parent.context).inflate(item_pilih_kata, parent, false),
                action
            )
            LessonConfig.BERBICARA_TYPE -> BerbicaraViewHolder(
                LayoutInflater.from(parent.context).inflate(item_berbicara, parent, false),
                activity, action
            )
            LessonConfig.BENAR_SALAH_TYPE -> BenarSalahViewHolder(
                LayoutInflater.from(parent.context).inflate(item_benar_salah, parent, false),
                action
            )
            LessonConfig.RESULT_TYPE -> ResultViewHolder(
                LayoutInflater.from(parent.context).inflate(item_result, parent, false),
                action
            )
            else -> Blank(
                LayoutInflater.from(parent.context).inflate(item_blank, parent, false),
                action
            )
        }
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(lessons[position])
        lessonActions[position] = holder
    }

    abstract class LessonViewHolder(
        view: View,
        val action: Action
    ) : RecyclerView.ViewHolder(view), LessonAction {
        abstract fun bind(lesson: Lesson)
    }

    interface LessonAction {
        fun onViewShowed()
    }

    class Blank(view: View, action: Action) : LessonViewHolder(view, action) {

        override fun bind(lesson: Lesson) {
            //TODO("Not yet implemented")
        }

        override fun onViewShowed() {
            //TODO("Not yet implemented")
        }
    }

    interface Action {
        fun showCorrectDisplay(correctionTitle: String?, correctWord: String?, correction: String?)
        fun showWrongDisplay(correctionTitle: String?, correctWord: String?, correction: String?)
        fun hideSolutionDisplay()
        fun questionAnswered()
    }

    fun getLessonActionFromPosition(position: Int): LessonAction? {
        return lessonActions[position]
    }

}