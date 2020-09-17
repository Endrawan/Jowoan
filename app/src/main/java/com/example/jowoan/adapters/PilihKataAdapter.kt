package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R
import com.example.jowoan.config.PilihKataConfig
import kotlinx.android.synthetic.main.item_pilih_kata_answer.view.*
import kotlinx.android.synthetic.main.item_pilih_kata_word.view.*

class PilihKataAdapter(val katas: List<Kata>) :
    RecyclerView.Adapter<PilihKataAdapter.PilihKataViewHolder>() {

    override fun getItemViewType(position: Int): Int = katas[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilihKataViewHolder {
        return when (viewType) {
            PilihKataConfig.ANSWER_TYPE -> AnswerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pilih_kata_answer, parent, false)
            )
            else -> WordViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pilih_kata_word, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = katas.size

    override fun onBindViewHolder(holder: PilihKataViewHolder, position: Int) {
        holder.bind(katas[position].word)
    }

    abstract class PilihKataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(word: String)
    }

    class WordViewHolder(view: View) : PilihKataViewHolder(view) {
        private val word = view.word

        override fun bind(word: String) {
            this.word.text = word
        }
    }

    class AnswerViewHolder(view: View) : PilihKataViewHolder(view) {
        private val answer = view.answer
        lateinit var word: String

        override fun bind(word: String) {
            this.word = word
            this.answer.text = replaceWithSpace(word)
        }

        private fun replaceWithSpace(word: String): String {
            var blank = ""
            for (i in word.indices) {
                blank += "_"
            }
            return blank
        }

        fun revealAnswer() {
            this.answer.text = word
        }
    }

    data class Kata(
        val word: String,
        val type: Int
    )
}