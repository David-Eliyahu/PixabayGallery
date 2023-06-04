package com.shutterfly.pixabaygallery.models.ui_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryItem(
    val id: Int,
    val previewUrl: String,
    val likes: Int
) : Parcelable