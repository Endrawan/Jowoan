package com.example.jowoan.adapters

import com.example.jowoan.R
import com.example.jowoan.adapters.dataTeman
import java.util.ArrayList

object dataTeman {

    private val nama = arrayOf("Andi"
    ,"budi",
    "amir")

    private val poin = arrayOf("100 poin",
        "200 poin","300 poin")

    private val avatar = intArrayOf(
        R.drawable.ava1,
        R.drawable.ava2,
        R.drawable.ava3
    )

    val listData : ArrayList<Teman>
    get() {

        val list = arrayListOf<Teman>()

        for (position in nama.indices){
            val teman = Teman( nama[position], poin[position],avatar[position])

            list.add(teman)
        }
        return list
    }
}