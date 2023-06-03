package com.shutterfly.pixabaygallery.models.network_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GalleryData(
    @SerializedName("hits")
    val galleryItems: List<GalleryItem>
)

@Parcelize
data class GalleryItem(
    val id: Int,
    @SerializedName("previewURL")
    val previewUrl: String,
    val likes : Int
) : Parcelable