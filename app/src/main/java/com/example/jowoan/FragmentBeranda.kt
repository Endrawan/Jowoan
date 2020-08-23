package com.example.jowoan

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jowoan.anim.Expandable
import com.example.jowoan.databinding.FragmentBerandaBinding
import com.example.jowoan.pengaturan.PengaturanActivity
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
        val binding: FragmentBerandaBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_beranda, container, false)


//
//        binding.ivPemula1Pelajaran2.setOnClickListener {
//            val intent = Intent(requireContext(), Activity_P1_2::class.java)
//            startActivity(intent)
//
//        }

        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        iv_pemula1_pelajaran2.setOnClickListener {
//            val intent = Intent(requireContext(), Activity_P1_2::class.java)
//            startActivity(intent)
//
//        }

        btn_arrow_pemula1.setOnClickListener {
            expandView(expand_pemula1)
        }

        /*btn_arrow_pemula2.setOnClickListener {


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

        btn_arrow_menengah1.setOnClickListener {


            if (expand_menengah1.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_menengah1.setVisibility(View.VISIBLE)

            } else {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_menengah1.setVisibility(View.GONE)

            }
        }

        btn_arrow_menengah2.setOnClickListener {


            if (expand_menengah2.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_menengah2.setVisibility(View.VISIBLE)

            } else {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_menengah2.setVisibility(View.GONE)

            }
        }

        btn_arrow_mahir1.setOnClickListener {


            if (expand_mahir1.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_mahir1.setVisibility(View.VISIBLE)

            } else {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_mahir1.setVisibility(View.GONE)

            }
        }

        btn_arrow_mahir2.setOnClickListener {


            if (expand_mahir2.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_mahir2.setVisibility(View.VISIBLE)

            } else {
                TransitionManager.beginDelayedTransition(
                    cardview,
                    AutoTransition()
                )
                expand_mahir2.setVisibility(View.GONE)

            }
        }*/
    }

    private fun expandView(view: View) {
        if (view.visibility == View.GONE)
            Expandable.expand(view)
        else
            Expandable.collapse(view)
    }
}
