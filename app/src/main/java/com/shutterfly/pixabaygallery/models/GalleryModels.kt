package com.shutterfly.pixabaygallery.models

import com.google.gson.annotations.SerializedName

data class GalleryData(
    @SerializedName("hits")
    val galleryItems: List<GalleryItem>
)

data class GalleryItem(
    val id: Int,
    val favorite: Boolean = false,
    @SerializedName("previewURL")
    val previewUrl: String,
    @SerializedName("likes")
    val likes: Long
)