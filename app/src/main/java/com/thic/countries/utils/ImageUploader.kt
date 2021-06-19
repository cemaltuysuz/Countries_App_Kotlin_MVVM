package com.thic.countries.utils

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

class ImageUploader : ImageUploaderI {
    override fun imageUploadWithGlide(context: Context, target: AppCompatImageView, url: String?) {
        if (url != null){
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(target)
        }
        }
    }

