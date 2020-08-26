package com.example.jowoan.internal

import android.content.Context
import android.widget.Toast

object Utils {
    fun toast(ctx: Context, msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }
}