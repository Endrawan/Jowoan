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


class LessonAdapter(private val lessons: MutableList<Lesson>, val activity: AppCompatActivity) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    override fun getItemCount(): Int = lessons.size

    override fun getItemViewType(position: Int): Int = lessons[position].type

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LessonViewHolder {
        return when (viewType) {
            LessonConfig.PENJELASAN_TYPE -> PenjelasanViewHolder(
                LayoutInflater.from(parent.context).inflate(item_penjelasan, parent, false)
            )
            LessonConfig.TIPS_TYPE -> TipsViewHolder(
                LayoutInflater.from(parent.context).inflate(item_tips, parent, false)
            )
            LessonConfig.HAFALAN_TYPE -> HafalanViewHolder(
                LayoutInflater.from(parent.context).inflate(item_hafalan, parent, false)
            )
            LessonConfig.PILIH_KATA_TYPE -> PilihKataViewHolder(
                LayoutInflater.from(parent.context).inflate(item_pilih_kata, parent, false)
            )
            LessonConfig.BERBICARA_TYPE -> BerbicaraViewHolder(
                LayoutInflater.from(parent.context).inflate(item_berbicara, parent, false), activity
            )
            LessonConfig.RESULT_TYPE -> ResultViewHolder(
                LayoutInflater.from(parent.context).inflate(item_result, parent, false)
            )
            else -> Blank(
                LayoutInflater.from(parent.context).inflate(item_blank, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(lessons[position])
    }

    abstract class LessonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(lesson: Lesson)
    }

    class Blank(view: View) : LessonViewHolder(view) {
        override fun bind(lesson: Lesson) {


        }
    }

}