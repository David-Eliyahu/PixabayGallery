package com.shutterfly.pixabaygallery.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.shutterfly.pixabaygallery.models.database_entities.FavoriteGalleryItem

@Dao
interface PixabayDao {

    @Insert
    suspend fun insertFavoriteItem(item: FavoriteGalleryItem)

    @Delete
    suspend fun deleteFavoriteItem(item: FavoriteGalleryItem)

    @Query("select * from Favorites where id = :itemId")
    suspend fun isFavoriteItem(itemId: Int): List<FavoriteGalleryItem>
}