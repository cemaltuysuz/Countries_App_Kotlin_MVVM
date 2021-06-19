package com.thic.countries.utils

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView

interface ImageUploaderI {

    fun imageUploadWithGlide(context: Context,target:AppCompatImageView,url:String?)
}