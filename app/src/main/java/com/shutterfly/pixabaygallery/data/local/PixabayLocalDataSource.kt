package com.shutterfly.pixabaygallery.data.local

import com.shutterfly.pixabaygallery.models.database_entities.FavoriteGalleryItem
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem

class PixabayLocalDataSource(
    private val pixabayDao: PixabayDao
    = PixabayDatabase.getInstance().pixabayDao()
) {

    /**
     * @return - boolean indicating true if the item is favorite and false if not.
     */
    suspend fun changeFavorite(item: GalleryItem) : Boolean{
        if (pixabayDao.isFavoriteItem(item.id).isEmpty()) {
            pixabayDao.insertFavoriteItem(FavoriteGalleryItem(item.id))
            return true
        }
        pixabayDao.deleteFavoriteItem(FavoriteGalleryItem(item.id))
        return false
    }

    suspend fun isFavoriteItem(item: GalleryItem) = pixabayDao.isFavoriteItem(item.id).isNotEmpty()
}