package com.shutterfly.pixabaygallery.data.remote

import com.shutterfly.pixabaygallery.core.constants.NetworkConstants.PIXABAY_SERVICE_KEY
import com.shutterfly.pixabaygallery.models.network_models.GalleryDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("?key=$PIXABAY_SERVICE_KEY&image_type=photo")
    suspend fun loadImagesByKey(
        @Query("per_page") loadSize: Int,
        @Query("page") page: Int,
        @Query("q") keyword: String
    ): GalleryDataResponse
}