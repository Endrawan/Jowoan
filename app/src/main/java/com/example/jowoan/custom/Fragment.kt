package com.example.jowoan.custom

import android.os.Bundle
import androidx.fragment.app.Fragment

open class Fragment : Fragment() {
    lateinit var activity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as AppCompatActivity
    }
}