package com.example.jowoan.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.models.lesson.Lesson
import kotlinx.android.synthetic.main.item_hafalan.view.*

class HafalanViewHolder(val view: View) : LessonAdapter.LessonViewHolder(view) {
    private val title = view.title
    private val image = view.image
    private val jowoLang = view.jowoLang
    private val indoLang = view.indoLang

    override fun bind(lesson: Lesson) {
        val hafalan = lesson.hafalan
        if (hafalan != null) {
            title.text = hafalan.title
            jowoLang.text = hafalan.jowoLang
            indoLang.text = hafalan.indoLang
            Glide.with(view.context).load(hafalan.image).into(image)
        }
    }
}