package com.shutterfly.pixabaygallery.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.shutterfly.pixabaygallery.datastore.FavoritesLocalDataSource
import com.shutterfly.pixabaygallery.network.GalleryPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class GalleryRepository(
    private val favoriteDataStore: FavoritesLocalDataSource,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    private val defaultPagingConfig by lazy {
        PagingConfig(pageSize = 30, enablePlaceholders = false)
    }

    private fun pagerFlow(keyword: String) = Pager(config = defaultPagingConfig) {
        GalleryPagingSource(keyword)
    }.flow.cachedIn(scope)

    fun searchImages(keyword: String) = combine(
        pagerFlow(keyword),
        favoriteDataStore.observeFavoritesIds()
    ) { galleryItems, favorites ->
        galleryItems.map { galleryItem ->
            galleryItem.copy(
                favorite = favorites.contains(galleryItem.id.toString())
            )
        }
    }

    suspend fun toggleFavorite(id: String) = withContext(Dispatchers.IO) {
        favoriteDataStore.toggleItem(id)
    }
}