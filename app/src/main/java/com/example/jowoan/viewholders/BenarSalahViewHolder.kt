package com.example.jowoan.viewholders

import android.view.View
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.models.lesson.Lesson
import kotlinx.android.synthetic.main.item_benar_salah.view.*

class BenarSalahViewHolder(view: View) : LessonAdapter.LessonViewHolder(view) {
    private val title = view.textView_title
    private val statement = view.textView_statement
    private val questions = view.textView_questions
    private val buttonTrue = view.button_true
    private val buttonFalse = view.button_false
    private val indicatorTrue = view.true_indicator
    private val indicatorFalse = view.false_indicator

    override fun bind(lesson: Lesson) {
        val benarSalah = lesson.benarSalah
        if (benarSalah != null) {
            title.text = benarSalah.title
            statement.text = benarSalah.statement
            questions.text = benarSalah.question
        }
    }
}