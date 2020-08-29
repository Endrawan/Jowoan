package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R.layout.item_subpractice
import com.example.jowoan.models.Subpractice
import kotlinx.android.synthetic.main.item_subpractice.view.*

class SubpracticeAdapter(private val subpractices: MutableList<Subpractice>) :
    RecyclerView.Adapter<SubpracticeAdapter.SubpracticeViewHolder>() {

    val TAG = "SubpracticeAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubpracticeViewHolder {
        return SubpracticeViewHolder(
            LayoutInflater.from(parent.context).inflate(item_subpractice, parent, false)
        )
    }

    override fun getItemCount() = subpractices.size

    override fun onBindViewHolder(holder: SubpracticeViewHolder, position: Int) {
        holder.bind(subpractices[position])
    }

    class SubpracticeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val line = view.line
        private val name = view.name
        private val title = view.title
        private val indicator = view.indicator
        private val duration = view.duration
        private val checklist = view.checklist

        fun bind(subpractice: Subpractice) {
            name.text = subpractice.name
            title.text = subpractice.title
            if (subpractice.ID % 2 == 0) {
                checklist.visibility = View.INVISIBLE
                duration.visibility = View.VISIBLE
            } else {
                checklist.visibility = View.VISIBLE
                duration.visibility = View.INVISIBLE
            }
        }
    }

}