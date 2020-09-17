package com.example.jowoan.views.main.fragmentToko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.jowoan.R
import kotlinx.android.synthetic.main.fragment_dialog.*

class TokoDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_beli.setOnClickListener {
            btn_beli.setBackgroundResource(R.drawable.box_abu)
            tv_toko_beli.text="Dimiliki"
            Toast.makeText(requireContext(),"asdasdasdasd",Toast.LENGTH_LONG).show()
        }
    }


}