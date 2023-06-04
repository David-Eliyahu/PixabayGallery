package com.shutterfly.pixabaygallery.models.network_models

import com.google.gson.annotations.SerializedName

data class GalleryDataResponse(
    @SerializedName("hits")
    val galleryResponseItems: List<GalleryResponseItem>
) {
    data class GalleryResponseItem(
        val id: Int,
        @SerializedName("previewURL")
        val previewUrl: String,
        val likes: Int
    )
}



