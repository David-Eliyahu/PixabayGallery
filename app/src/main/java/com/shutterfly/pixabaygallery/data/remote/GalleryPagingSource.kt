package com.shutterfly.pixabaygallery.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shutterfly.pixabaygallery.core.constants.NetworkConstants.PIXABAY_BASE_URL
import com.shutterfly.pixabaygallery.models.ui_models.GalleryItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GalleryPagingSource(private val keyword: String) : PagingSource<Int, GalleryItem>() {


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(PIXABAY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val pixabayService by lazy {
        retrofit.create(PixabayService::class.java)
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        // Key is null when we are loading for the first time. Set current page to load the first page
        val currentPage = params.key ?: 1

        return try {
            val images = pixabayService.loadImagesByKey(params.loadSize, currentPage, keyword)

            val galleryItems = mutableListOf<GalleryItem>()
            images.galleryResponseItems.forEach { responseItem ->
                galleryItems.add(GalleryItem(responseItem.id,responseItem.previewUrl,responseItem.likes))
            }

            LoadResult.Page(
                data = galleryItems,
                // If we are on the first page, the previous key should be null
                prevKey = if (currentPage > 1) currentPage - 1 else null,
                // Null if we reach the end and we are not getting data anymore
                nextKey = if (galleryItems.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}