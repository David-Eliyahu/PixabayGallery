package com.shutterfly.pixabaygallery.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.shutterfly.pixabaygallery.data.local.PixabayLocalDataSource
import com.shutterfly.pixabaygallery.data.remote.GalleryPagingSource
import com.shutterfly.pixabaygallery.models.ui_models.GalleryItem

class GalleryRepository(
    private val pixabayLocalDataSource: PixabayLocalDataSource = PixabayLocalDataSource()
) {

    private val defaultPagingConfig by lazy { PagingConfig(pageSize = 30, enablePlaceholders = false) }

    fun searchImages(keyword: String) = Pager(config = defaultPagingConfig) {
        GalleryPagingSource(keyword)
    }.liveData

    suspend fun isFavoriteItem(item: GalleryItem) = pixabayLocalDataSource.isFavoriteItem(item)

    suspend fun changeFavorite(item: GalleryItem) = pixabayLocalDataSource.changeFavorite(item)
}