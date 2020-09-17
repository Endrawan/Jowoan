package com.example.jowoan.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.adapters.PilihKataAdapter
import com.example.jowoan.config.PilihKataConfig
import com.example.jowoan.models.lesson.Lesson
import com.example.jowoan.models.lesson.PilihKata
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.item_pilih_kata.view.*

class PilihKataViewHolder(val view: View) : LessonAdapter.LessonViewHolder(view) {
    private val title = view.title
    private val image = view.image
    private val question = view.recyclerView_question
    private val answer1 = view.answer1
    private val answer2 = view.answer2
    private val answer1Indicator = view.answer1_indicator
    private val answer2Indicator = view.answer2_indicator
    private var choosenAnswerID: Int = 0
    private var answer: Int = 0

    override fun bind(lesson: Lesson) {
        val pilihKata = lesson.pilihKata
        if (pilihKata != null) {
            title.text = pilihKata.title
            Glide.with(view.context).load(pilihKata.image).into(image)

            prepareAnswer(pilihKata)
            answer1.setOnClickListener {
                choosenAnswerID = 0
                showAnswer()
            }
            answer2.setOnClickListener {
                choosenAnswerID = 1
                showAnswer()
            }

            val layoutManager = FlexboxLayoutManager(view.context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START
            question.layoutManager = layoutManager
            val adapter = PilihKataAdapter(prepareWord(pilihKata))
            question.adapter = adapter
        }
    }

    private fun prepareWord(pilihKata: PilihKata): List<PilihKataAdapter.Kata> {
        val katas = mutableListOf<PilihKataAdapter.Kata>()
        val firstWord = pilihKata.question.substring(0, pilihKata.charStart)
        val secondWord = pilihKata.question.substring(
            pilihKata.charStart,
            pilihKata.charStart + pilihKata.charLength
        )

        katas.add(PilihKataAdapter.Kata(firstWord, PilihKataConfig.WORD_TYPE))
        katas.add(PilihKataAdapter.Kata(secondWord, PilihKataConfig.ANSWER_TYPE))

        if (pilihKata.charStart + pilihKata.charLength < pilihKata.question.length) {
            val thirdWord = pilihKata.question.substring(pilihKata.charStart + pilihKata.charLength)
            katas.add(PilihKataAdapter.Kata(thirdWord, PilihKataConfig.WORD_TYPE))
        }
        return katas
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

    private fun showAnswer() {
        answer1Indicator.visibility = View.VISIBLE
        answer2Indicator.visibility = View.VISIBLE
        val vh =
            question.getChildViewHolder(question.getChildAt(1)) as PilihKataAdapter.AnswerViewHolder
        vh.revealAnswer()
        answer1.isEnabled = false
        answer2.isEnabled = false
        if (answer == choosenAnswerID) {
            // TODO Add correct reaction
        } else {
            // TODO Add wrong reaction
        }
    }
}