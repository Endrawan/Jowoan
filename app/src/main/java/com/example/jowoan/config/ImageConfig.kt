package com.example.jowoan.config

import androidx.core.content.res.ResourcesCompat
import com.example.jowoan.R
import com.example.jowoan.custom.App

object ImageConfig {
    val imageNotFound =
        ResourcesCompat.getDrawable(App.resourses!!, R.drawable.image_not_found, null)
    val defaultAvatar = ResourcesCompat.getDrawable(App.resourses!!, R.drawable.avatar_basic, null)
}