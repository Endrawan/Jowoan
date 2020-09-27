package com.example.jowoan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jowoan.R.layout.item_subpractice
import com.example.jowoan.models.Subpractice
import kotlinx.android.synthetic.main.item_subpractice.view.*

class SubpracticeAdapter(
    private val subpractices: MutableList<Subpractice>,
    private val action: PracticeAdapter.Action
) :
    RecyclerView.Adapter<SubpracticeAdapter.SubpracticeViewHolder>() {

    val TAG = "SubpracticeAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubpracticeViewHolder {
        return SubpracticeViewHolder(
            LayoutInflater.from(parent.context).inflate(item_subpractice, parent, false)
        )
    }

    override fun getItemCount() = subpractices.size

    override fun onBindViewHolder(holder: SubpracticeViewHolder, position: Int) {
        holder.bind(subpractices[position], action, position,subpractices.size)
    }

    class SubpracticeViewHolder(view: View) : RecyclerView.ViewHolder(view) {


//        private val line = view.line
        private val name = view.name
        private val title = view.title
        private val indicator = view.indicator
        private val indicatoractive = view.indicatoractive
        private val indicator2 = view.indicatorlayout2
        private val indicator2active = view.indicatorlayout2active

        private val duration = view.duration
        private val checklist = view.checklist
        private val layout1 = view.layout_1garis
        private val layout2 = view.layout_2garis
        private val linelayout2 = view.linelayout2

        fun bind(subpractice: Subpractice, action: PracticeAdapter.Action, position: Int, size: Int) {
            if (position == 0) {
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.GONE
            } else {
                layout1.visibility = View.GONE
                layout2.visibility = View.VISIBLE
            }


            name.text = subpractice.name
            title.text = subpractice.title

            if (subpractice.completionStatus) {
                checklist.visibility = View.VISIBLE
                duration.visibility = View.INVISIBLE
                if (layout1.visibility==View.VISIBLE){
                    indicator.visibility =View.GONE
                    indicatoractive.visibility=View.VISIBLE
                }else{
                    indicator2.visibility = View.GONE
                    indicator2active.visibility= View.VISIBLE
                }
            } else {
                checklist.visibility = View.INVISIBLE
                duration.visibility = View.VISIBLE
            }
            checklist.setOnClickListener {
                action.subpracticeClicked(subpractice)
            }
            duration.setOnClickListener {
                action.subpracticeClicked(subpractice)
            }
        }
    }

}