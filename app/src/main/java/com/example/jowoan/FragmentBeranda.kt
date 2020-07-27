package com.example.jowoan

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jowoan.databinding.FragmentBerandaBinding
import kotlinx.android.synthetic.main.fragment_beranda.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentBeranda : Fragment() {
    var arrowBtn: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentBerandaBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_beranda, container, false)



        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_arrow_pemula1.setOnClickListener{


            if (expand_pemula1.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(
                   cardview,
                    AutoTransition()
                )
                expand_pemula1.setVisibility(View.VISIBLE)

            } else {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_pemula1.setVisibility(View.GONE)

            }
        }

        btn_arrow_pemula2.setOnClickListener{


            if (expand_pemula2.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_pemula2.setVisibility(View.VISIBLE)

            } else {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_pemula2.setVisibility(View.GONE)

            }
        }
    }
}
