package com.example.jowoan.viewholders

import android.view.View
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.config.LessonConfig
import com.example.jowoan.models.lesson.BenarSalah
import com.example.jowoan.models.lesson.Lesson
import kotlinx.android.synthetic.main.item_benar_salah.view.*

class BenarSalahViewHolder(view: View, action: LessonAdapter.Action) :
    LessonAdapter.LessonViewHolder(view, action) {
    private val title = view.textView_title
    private val statement = view.textView_statement
    private val questions = view.textView_questions
    private val buttonTrue = view.button_true
    private val buttonFalse = view.button_false
    private val indicatorTrue = view.true_indicator
    private val indicatorFalse = view.false_indicator
    private lateinit var lesson: Lesson

    override fun bind(lesson: Lesson) {
        this.lesson = lesson
        val benarSalah = lesson.benarSalah
        if (benarSalah != null) {
            title.text = benarSalah.title
            statement.text = benarSalah.statement
            questions.text = benarSalah.question

            disableAnswerOption()

            buttonTrue.setOnClickListener {
                checkAnswer(benarSalah, true)
            }

            buttonFalse.setOnClickListener {
                checkAnswer(benarSalah, false)
            }
        }
    }

    override fun onViewShowed() {
        enableAnswerOption()
    }

    private fun checkAnswer(benarSalah: BenarSalah, userAnswer: Boolean) {
        if (benarSalah.answer == userAnswer) {
            // TODO Change Indicator
            action.questionAnswered(LessonConfig.ANSWER_CORRECT)
            action.showCorrectDisplay(null, null, benarSalah.correction)
        } else {
            // TODO Change Indicator
            action.questionAnswered(LessonConfig.ANSWER_WRONG)
            action.showWrongDisplay(null, null, benarSalah.correction)
            action.retryNextTime(lesson)
        }
        disableAnswerOption()
    }

    private fun disableAnswerOption() {
        buttonTrue.isEnabled = false
        buttonFalse.isEnabled = false
    }

    private fun enableAnswerOption() {
        buttonTrue.isEnabled = true
        buttonFalse.isEnabled = true
    }
}