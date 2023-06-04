package com.shutterfly.pixabaygallery.models.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shutterfly.pixabaygallery.core.constants.Constants.FAVORITE_ITEMS_TABLE

@Entity(tableName = FAVORITE_ITEMS_TABLE)
data class FavoriteGalleryItem(@PrimaryKey val id: Int)
