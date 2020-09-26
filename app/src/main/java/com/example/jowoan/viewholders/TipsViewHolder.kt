package com.example.jowoan.viewholders

import android.view.View
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.models.lesson.Lesson
import kotlinx.android.synthetic.main.item_tips.view.*

class TipsViewHolder(view: View, action: LessonAdapter.Action) :
    LessonAdapter.LessonViewHolder(view, action) {
    private val title = view.title
    private val content = view.content

    override fun bind(lesson: Lesson) {
        action.questionAnswered()
        val tips = lesson.tips
        if (tips != null) {
            title.text = tips.title
            content.text = tips.content
        }
    }

    override fun onViewShowed() {
        action.questionAnswered()
    }
}