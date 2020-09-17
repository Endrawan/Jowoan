package com.example.jowoan.views.pengaturan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jowoan.R
import com.example.jowoan.custom.Fragment
import com.example.jowoan.databinding.FragmentTelponBinding
import com.example.jowoan.internal.PhoneUtils
import kotlinx.android.synthetic.main.fragment_telpon.*
import kotlinx.android.synthetic.main.view_progress.*

class TelponFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTelponBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_telpon, container, false)

        binding.buttonBack.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_telponFragment_to_fragmentPengaturan2)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText_phone.setText(PhoneUtils.toInternationalStandard(activity.user.phone))
        button_submit.setOnClickListener { validateAndUpdatePhone() }
    }

    private fun validateAndUpdatePhone() {
        val phone = editText_phone.text.toString().trim()
        if (phone.length < 6) {
            activity.toast("Nomer telepon invalid!")
            return
        }
        activity.user.phone = PhoneUtils.toLocalStandard(phone)

        showLoading("Mengubah telpon di Database...")
        val a = activity as PengaturanActivity
        a.updateBackend("telpon")
    }

    private fun showLoading(message: String) {
        progressBar?.visibility = View.VISIBLE
        progressMessage.text = message
    }

    private fun hideLoading() {
        progressBar?.visibility = View.INVISIBLE
        progressMessage.text = ""
    }

}