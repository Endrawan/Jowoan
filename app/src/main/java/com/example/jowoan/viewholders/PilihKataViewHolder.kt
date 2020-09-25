package com.example.jowoan.viewholders

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.jowoan.R
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.custom.App
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.lesson.Lesson
import com.example.jowoan.models.lesson.PilihKata
import kotlinx.android.synthetic.main.item_pilih_kata.view.*

class PilihKataViewHolder(val view: View) : LessonAdapter.LessonViewHolder(view) {
    private val title = view.title
    private val image = view.image
    private val question = view.question
    private val answer1 = view.answer1
    private val answer2 = view.answer2
    private val answer1Indicator = view.answer1_indicator
    private val answer2Indicator = view.answer2_indicator
    private var answerColor: Int = 0
    private var choosenAnswerID: Int = 0
    private var answer: Int = 0

    override fun bind(lesson: Lesson) {
        val pilihKata = lesson.pilihKata
        if (pilihKata != null) {
            title.text = pilihKata.title

            val placeholder =
                ResourcesCompat.getDrawable(App.resourses!!, R.drawable.image_not_found, null)
            GlideApp.with(view.context).load(pilihKata.image).placeholder(placeholder).centerCrop()
                .into(image)

            answerColor = ResourcesCompat.getColor(App.resourses!!, R.color.blue, null)
            val plainQuestion = pilihKata.question.replaceRange(
                pilihKata.charStart,
                pilihKata.charStart + pilihKata.charLength, getBlankString(pilihKata.charLength)
            )
            val spannableQuestion = SpannableStringBuilder(plainQuestion).apply {
                setSpan(
                    ForegroundColorSpan(answerColor),
                    pilihKata.charStart, pilihKata.charStart + pilihKata.charLength,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            question.text = spannableQuestion

            answer1.setOnClickListener {
                choosenAnswerID = 0
                showAnswer(pilihKata)
            }
            answer2.setOnClickListener {
                choosenAnswerID = 1
                showAnswer(pilihKata)
            }

            prepareAnswer(pilihKata)
        }
    }

    private fun getBlankString(length: Int): String {
        var result = ""
        for (i in 0 until length) {
            result += "_"
        }
        return result
    }

    private fun prepareAnswer(pilihKata: PilihKata) {
        answer = (0..1).random()
        if (answer == 0) {
            answer1.text = pilihKata.answers[0].answer
            answer2.text = pilihKata.answers[1].answer
            // TODO Change indicator to proper one
        } else {
            answer1.text = pilihKata.answers[1].answer
            answer2.text = pilihKata.answers[0].answer
            // TODO Change indicator to proper one
        }
    }

    private fun showAnswer(pilihKata: PilihKata) {
//        answer1Indicator.visibility = View.VISIBLE
//        answer2Indicator.visibility = View.VISIBLE
//        answer1.isEnabled = false
//        answer2.isEnabled = false
        val spannableQuestion = SpannableStringBuilder(pilihKata.question).apply {
            setSpan(
                ForegroundColorSpan(answerColor),
                pilihKata.charStart, pilihKata.charStart + pilihKata.charLength,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        question.text = spannableQuestion
        if (answer == choosenAnswerID) {
            // TODO Add correct reaction
        } else {
            // TODO Add wrong reaction
        }
    }
}