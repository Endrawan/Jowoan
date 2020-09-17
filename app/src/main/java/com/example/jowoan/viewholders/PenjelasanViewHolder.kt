package com.example.jowoan.viewholders

import android.view.View
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.models.lesson.Lesson
import kotlinx.android.synthetic.main.item_penjelasan.view.*

class PenjelasanViewHolder(view: View) : LessonAdapter.LessonViewHolder(view) {
    private val title = view.title
    private val content = view.content

    override fun bind(lesson: Lesson) {
        val penjelasan = lesson.penjelasan
        if (penjelasan != null) {
            title.text = penjelasan.title
            content.text = penjelasan.content
        }
    }
}