package com.example.jowoan.adapters

import com.example.jowoan.R
import java.util.ArrayList


object dataActivityTeman {

    private val nama = arrayOf("Andi"
        ,"budi",
        "amir")

    private val kegiatan = arrayOf("telah belajar rutin 1 minggu",
        "telah belajar rutin 1 minggu","telah belajar rutin 1 minggu")

    private val avatar = intArrayOf(
        R.drawable.ava1,
        R.drawable.ava2,
        R.drawable.ava3
    )

    val listDataActivity : ArrayList<AktifitasTeman>
        get() {

            val list = arrayListOf<AktifitasTeman>()

            for (position in nama.indices){
                val aktifitasTeman = AktifitasTeman( nama[position], kegiatan[position],avatar[position])

                list.add(aktifitasTeman)
            }
            return list
        }
}