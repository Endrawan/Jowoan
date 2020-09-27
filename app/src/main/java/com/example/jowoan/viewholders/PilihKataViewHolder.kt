package com.example.jowoan.viewholders

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.jowoan.R
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.config.LessonConfig
import com.example.jowoan.custom.App
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.lesson.Lesson
import com.example.jowoan.models.lesson.PilihKata
import kotlinx.android.synthetic.main.item_pilih_kata.view.*

class PilihKataViewHolder(val view: View, action: LessonAdapter.Action) :
    LessonAdapter.LessonViewHolder(view, action) {
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
    private lateinit var lesson: Lesson

    override fun bind(lesson: Lesson) {
        this.lesson = lesson
        val pilihKata = lesson.pilihKata
        if (pilihKata != null) {
            title.text = pilihKata.title

            disableAnswerOption()

            GlideApp.with(view.context).load("http://${pilihKata.image}")
                .placeholder(ImageConfig.imageNotFound).centerCrop()
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

    override fun onViewShowed() {
        enableAnswerOption()
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
            action.questionAnswered(LessonConfig.ANSWER_CORRECT)
            action.showCorrectDisplay(
                "Jawaban Yang Benar:",
                pilihKata.question,
                pilihKata.correction
            )
        } else {
            // TODO Add wrong reaction
            action.questionAnswered(LessonConfig.ANSWER_WRONG)
            action.showWrongDisplay("Jawaban Yang Benar:", pilihKata.question, pilihKata.correction)
            action.retryNextTime(lesson)
        }
        disableAnswerOption()
    }

    private fun enableAnswerOption() {
        answer1.isEnabled = true
        answer2.isEnabled = true
    }

    private fun disableAnswerOption() {
        answer1.isEnabled = false
        answer2.isEnabled = false
    }
}