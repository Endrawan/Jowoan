package com.example.jowoan.viewholders

import android.view.View
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.models.lesson.Lesson
import kotlinx.android.synthetic.main.item_result.view.*

class ResultViewHolder(view: View) : LessonAdapter.LessonViewHolder(view) {
    private val image = view.image
    private val points = view.points
    private val title = view.title
    private val subtitle = view.subtitle

    override fun bind(lesson: Lesson) {
        val lessonResult = lesson.result
        if (lessonResult != null) {
            points.text = "+ ${lessonResult.point} pts"
            title.text = lessonResult.title
            subtitle.text = lessonResult.subtitle
        }
    }
}