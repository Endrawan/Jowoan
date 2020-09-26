package com.example.jowoan.viewholders

import android.view.View
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.config.LessonConfig
import com.example.jowoan.models.lesson.Lesson
import kotlinx.android.synthetic.main.item_penjelasan.view.*

class PenjelasanViewHolder(view: View, action: LessonAdapter.Action) :
    LessonAdapter.LessonViewHolder(view, action) {
    private val title = view.title
    private val content = view.content

    override fun bind(lesson: Lesson) {
        action.questionAnswered(LessonConfig.ANSWER_CORRECT)
        val penjelasan = lesson.penjelasan
        if (penjelasan != null) {
            title.text = penjelasan.title
            content.text = penjelasan.content
        }
    }

    override fun onViewShowed() {
        action.questionAnswered(LessonConfig.ANSWER_CORRECT)
    }
}